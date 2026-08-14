package io.cloud4s.cli

import AppConfigs.Config
import ssh.aliases.ssh_session
import ssh.constants.*
import ssh.all.*
import ssh.enumerations.ssh_auth_e.SSH_AUTH_SUCCESS
import ssh.enumerations.ssh_options_e.*

import scala.scalanative.unsafe
import scala.scalanative.unsafe.*
import scala.scalanative.unsafe.Ptr
import scala.scalanative.unsigned.*
import scala.util.Try

object Ssh:

  val SSH_OK: Int = 0

  def connectAndExec(
      cmd: String,
      host: String
  ): Config ?=> Zone ?=> Try[String] =
    connect(host)(exec(cmd))

  private def connect(host: String)(
      f: ssh_session => Try[String]
  ): Config ?=> Zone ?=> Try[String] =
    Try:
      val cfg = summon[Config]
      val session = ssh_new()

      if session.asInstanceOf[Ptr[?]] == null
      then throw new Exception("Failed to create ssh session")

      val verbosity: Ptr[CUnsignedInt] = alloc[CUnsignedInt](1)
      !verbosity = SSH_LOG_NOLOG // SSH_LOG_PROTOCOL

      val port: Ptr[CInt] = alloc[CInt](1)
      !port = cfg.port

      ssh_options_set(session, SSH_OPTIONS_HOST, toCString(host))
      ssh_options_set(
        session,
        SSH_OPTIONS_LOG_VERBOSITY,
        verbosity.asInstanceOf[Ptr[Byte]]
      )
      ssh_options_set(session, SSH_OPTIONS_PORT, port.asInstanceOf[Ptr[Byte]])

      val knownHostsPath = s"${System.getenv("HOME")}/.ssh/known_hosts"

      ssh_options_set(
        session,
        SSH_OPTIONS_KNOWNHOSTS,
        toCString(knownHostsPath)
      )
      // ssh_options_set(session, SSH_OPTIONS_GLOBAL_KNOWNHOSTS, c"/dev/null")

      if ssh_connect(session) != SSH_OK
      then
        throw new Exception(
          s"Failed to connect to ssh server: ${ssh_get_error(session.asInstanceOf[Ptr[Byte]])}"
        )

      session
    .flatMap: session =>
      val r = f(session)
      ssh_disconnect(session)
      ssh_free(session)
      r

  private def exec(cmd: String)(session: ssh_session): Zone ?=> Try[String] =
    Try:

      // authenticate
      // https://api.libssh.org/stable/libssh_tutor_authentication.html
      val rf = ssh_userauth_publickey_auto(session, null, null)
      if rf != SSH_AUTH_SUCCESS.asInstanceOf[CInt]
      then throw new Exception(s"Failed to auth session: $rf")

      // open channel
      val channel = ssh_channel_new(session)
      if channel.asInstanceOf[Ptr[?]] == null
      then throw new Exception("Failed to create ssh channel")

      // open session
      if ssh_channel_open_session(channel) != SSH_OK
      then
        ssh_channel_free(channel)
        throw new Exception("Failed to open ssh channel session")

      // send a command
      if ssh_channel_request_exec(channel, toCString(cmd)) != SSH_OK
      then
        ssh_channel_close(channel)
        ssh_channel_free(channel)
        throw new Exception("Failed to execute ssh channel command")

      val result = new StringBuilder
      val buffer = alloc[Byte](4096)

      val size: uint32_t = (unsafe.sizeOf[Byte] * 4096).toUInt

      var n = ssh_channel_read(channel, buffer, size, 0)

      while n > 0 do
        result.append(fromCStringSlice(buffer, n.toCSSize.toCSize))
        n = ssh_channel_read(channel, buffer, size, 0)

      if n < 0
      then
        ssh_channel_close(channel)
        ssh_channel_free(channel)
        throw new Exception("Failed to execute ssh channel command")

      ssh_channel_send_eof(channel)
      ssh_channel_close(channel)
      ssh_channel_free(channel)

      result.toString()
