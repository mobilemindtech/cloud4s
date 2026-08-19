package io.cloud4s.cli

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

object Logger:
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def info(s: String) =
    Console.print(Console.GREEN)
    Console.print(
      s"[${LocalDateTime.now().format(formatter)}][INFO]::>\n"
    )
    Console.print(s"\n$s\n")
    Console.print(Console.RESET)

  def error(s: String) =
    Console.print(Console.RED)
    Console.print(
      s"[${LocalDateTime.now().format(formatter)}][ERROR]::>\n"
    )
    Console.print(s"\n$s\n")
    Console.print(Console.RESET)
