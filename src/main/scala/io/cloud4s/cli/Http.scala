package io.cloud4s.cli

import cats.effect.IO
import sttp.client4.*
import sttp.model.Uri

import java.util.Base64

sealed trait Auth

case class Response(statusCode: Int, body: String)

case class AuthBasic(username: String, password: String) extends Auth:

  val credential = s"${username}:${password}"

  def encode(): String = new String(encodeBytes())

  def encodeBytes(): Array[Byte] =
    Base64.getEncoder
      .encode(credential.getBytes)

case class Http(
    url: String,
    auth: Option[Auth] = None,
    headers: Map[String, String] = Map(),
    timeout: Long = 5000
):

  private def addHeaders(
      req: Request[Either[String, String]]
  ): Request[Either[String, String]] =
    val req2 = req
      .header("Content-Type", "application/json")
      .header("Accept", "application/json")

    auth match
      case Some(basic: AuthBasic) =>
        req2.header("Authorization", s"Basic ${basic.encode()}")
      case _ => req2

  private def parseUri(): IO[Uri] =
    Uri.parse(url) match
      case Left(err) =>
        IO.raiseError(new Exception(s"error to parse uri: $err"))
      case Right(uri) => IO.pure(uri)

  def post(payload: String): IO[Response] =
    parseUri().flatMap { uri =>
      val backend = DefaultSyncBackend()
      val request = basicRequest
        .post(uri)
        .body(payload)
      val response = addHeaders(request).send(backend)
      val body = response.body match
        case Left(body)  => body
        case Right(body) => body
      IO.pure(Response(response.code.code, body))
    }

  def get: IO[Response] =
    parseUri().flatMap { uri =>
      val backend = DefaultSyncBackend()
      val request = basicRequest.get(uri)
      val response = addHeaders(request).send(backend)
      val body = response.body match
        case Left(body)  => body
        case Right(body) => body
      IO.pure(Response(response.code.code, body))
    }
