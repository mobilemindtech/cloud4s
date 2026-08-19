package io.cloud4s.cli

import scala.util.Try

case class SshConfig(host: String, port: Int)

trait Ssh:
  def connectAndExec(
      cmd: String,
      cfg: SshConfig
  ): Try[String]
