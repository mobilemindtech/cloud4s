package io.cloud4s.cli

import sttp.client4.*
import sttp.model.Uri

import java.util.Base64
import scala.util.Try

extension [A, B](value: Either[A, B])
  inline def toTryWith(inline f: A => Exception): Try[B] =
    value.left.map(f).toTry

trait BackendProvider:
  def backend(): SyncBackend

case class Response(statusCode: Int, body: String)

object Response:
  def apply(resp: sttp.client4.Response[Either[String, String]]): Response =
    val body = resp.body match
      case Left(body)  => body
      case Right(body) => body
    Response(resp.code.code, body)

sealed trait Auth

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
)(using BackendProvider):

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

  private def parseUri(): Try[Uri] =
    Uri
      .parse(url)
      .toTryWith(s => new Exception(s"Parse error: $s"))

  def post(payload: String): Try[Response] =
    parseUri()
      .map: uri =>
        val backend = summon[BackendProvider].backend()
        val request = basicRequest
          .post(uri)
          .body(payload)
        val resp = addHeaders(request).send(backend)
        Response(resp)

  def get: Try[Response] =
    parseUri()
      .map: uri =>
        val backend = summon[BackendProvider].backend()
        val request = basicRequest.get(uri)
        val resp = addHeaders(request).send(backend)
        Response(resp)
