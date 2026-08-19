package io.cloud4s.cli

import java.io.File
import scala.annotation.tailrec
import scala.io.Source
import scala.util.Try
import java.io.FileNotFoundException
import scala.util.Using

object AppConfigs:

  case class ValidationError(msg: String) extends Exception(msg)

  case class Config(
      hosts: Seq[String] = Nil,
      hostMain: String = "",
      port: Int = 0,
      logsPath: String = "",
      codebuildUrl: String = "",
      codebuildUsername: String = "",
      codebuildPassword: String = "",
      validations: Seq[String] = Seq.empty
  ):

    private def isEmpty(text: String) = text == null || text.isBlank

    private def checkField(name: String, value: String): Config =
      if value == null || value.isBlank
      then copy(validations = validations :+ s"value $name required")
      else this

    def valid = validations.isEmpty

    def validate(): Config =
      def fields = Seq(
        ("hostMain", hostMain),
        ("logsPath", logsPath),
        ("codebuildUrl", codebuildUrl),
        ("codebuildUsername", codebuildUsername),
        ("codebuildPassword", codebuildPassword),
        ("port", port),
        ("hosts", hosts)
      )

      fields.foldLeft(this) { case (acc, (name, value)) =>
        name match
          case "port" if port == 0 =>
            copy(validations = validations :+ s"value $name required")
          case "hosts" if hosts.isEmpty =>
            copy(validations = validations :+ s"value $name required")
          case value: String => acc.checkField(name, value)
      }

  private def home = System.getenv("HOME")

  @tailrec
  private def readLines(lines: Seq[String], config: Config = Config()): Config =
    lines match
      case Nil          => config
      case line :: rest =>
        if line.startsWith("#")
        then readLines(rest, config)
        else
          line.split("=").toList match
            case "hosts" :: hosts :: Nil =>
              readLines(rest, config.copy(hosts = hosts.split(",")))
            case "host.main" :: main :: Nil =>
              readLines(rest, config.copy(hostMain = main))
            case "ssh.port" :: port :: Nil =>
              readLines(rest, config.copy(port = port.toInt))
            case "logs.path" :: logsPath :: Nil =>
              readLines(
                rest,
                config.copy(logsPath = logsPath.replace("$HOME", home))
              )
            case "codebuild.url" :: url :: Nil =>
              readLines(rest, config.copy(codebuildUrl = url))
            case "codebuild.username" :: username :: Nil =>
              readLines(rest, config.copy(codebuildUsername = username))
            case "codebuild.password" :: password :: Nil =>
              readLines(rest, config.copy(codebuildPassword = password))
            case _ =>
              println(s"wrong config: ${line}")
              readLines(rest, config)

  def getConfigs(): Try[Config] =
    Try:
      val file = new File(home, ".cloud4s")

      if !file.exists()
      then
        throw new FileNotFoundException(
          s"file ${file.getAbsolutePath()} not exists"
        )
      file
    .flatMap: file =>
      Using(Source.fromFile(file)): buff =>
        val cfg = readLines(buff.getLines().toSeq)
        cfg.validate() match
          case c if c.valid => cfg
          case c            =>
            throw new ValidationError(
              s"Validation error: \n${c.validations.mkString("\n")}"
            )
