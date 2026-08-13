package io.cloud4s.cli

import cats.effect.IO
import sttp.client4.*
import sttp.model.Uri

import java.util.Base64
import scala.scalanative.unsafe.extern

@extern
object libcurl {
  // CURL_GLOBAL_ALL = 3 (Inicializa SSL, sockets e rotinas globais)
  def curl_global_init(flags: Long): Int = extern
}

def curl_init() = libcurl.curl_global_init(3L)

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

  private def parseUri(): IO[Uri] = {
    IO.delay {
      Uri.parse(url) match
        case Left(err) =>
          throw new Exception(s"error to parse uri: $err")
        case Right(uri) => uri
    }
  }

  def post(payload: String): IO[Response] =
    parseUri().flatMap { uri =>
      IO.delay {
        val backend = DefaultSyncBackend()
        val request = basicRequest
          .post(uri)
          .body(payload)
        val response = addHeaders(request).send(backend)
        val body = response.body match
          case Left(body)  => body
          case Right(body) => body
        Response(response.code.code, body)
      }
    }

  def get: IO[Response] =
    parseUri().flatMap { uri =>
      IO.delay {
        println(s"GET: $uri")
        val backend = DefaultSyncBackend()
        val req1 = basicRequest.get(uri)
        println("created request")
        val req2 = addHeaders(req1)
        println("add headers")
        val response = req2.send(backend)
        println("send request ok")
        val body = response.body match
          case Left(body)  => body
          case Right(body) => body
        Response(response.code.code, body)
      }
    }
