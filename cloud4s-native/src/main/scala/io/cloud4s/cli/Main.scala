package io.cloud4s.cli

import sttp.client4.SyncBackend
import sttp.client4.curl.CurlBackend

given s: Ssh = SshImpl

given backendProvider: BackendProvider = new BackendProvider:
  def backend(): SyncBackend = CurlBackend()

@main def main(args: String*) =
  Cli.run(args*)
