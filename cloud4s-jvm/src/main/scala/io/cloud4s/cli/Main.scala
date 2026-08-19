package io.cloud4s.cli

import scala.util.Try
import sttp.client4.SyncBackend
import sttp.client4.httpclient.HttpClientSyncBackend

given Ssh:
  def connectAndExec(
      cmd: String,
      cfg: SshConfig
  ): Try[String] = ???

given backendProvider: BackendProvider = new BackendProvider:
  def backend(): SyncBackend = HttpClientSyncBackend()

@main def main(args: String*) =
  Cli.run(args*)
