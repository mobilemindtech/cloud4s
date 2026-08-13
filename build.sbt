import sbt.*
import sbt.Keys.*

import java.io.File
import scala.language.postfixOps
import scala.sys.process.*
import scala.scalanative.build.*
import bindgen.interface.Binding
import bindgen.plugin.BindgenMode
import com.indoorvivants.detective.Platform

resolvers += Resolver.mavenLocal


scalaVersion := "3.8.4"
scalafmtOnCompile := true
organization := "io.cloud4s.cli"
name := "cloud4s"
version := "0.0.4"
scalacOptions ++= Seq(
  "-new-syntax",
  "-Wvalue-discard",
  "-Wunused:all",
  "-deprecation",
  "-explain",
  "-explain-cyclic",
  "-rewrite",
  "-source:future"
)
Compile / run / fork := true
usePipelining := true
ThisBuild / envVars := Map(
  "ENVIRONMENT" -> "development"
)

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin, BindgenPlugin)
  .settings(
    name := "cloud4s",

    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.6.0",
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.6.0",
      "org.typelevel" %%% "cats-effect" % "3.7.0",
      "com.monovore" %%% "decline" % "2.6.2",
      "com.monovore" %%% "decline-effect" % "2.6.2",
      "com.lihaoyi" %%% "upickle" % "4.4.3",
      "com.softwaremill.sttp.client4" %%% "core" % "4.0.26",
      "org.scalameta" %%% "munit" % "1.3.5" % Test
    ),

   
    // 2. Modos e caminhos de saída do Bindgen
    bindgenMode := BindgenMode.Manual(
      scalaDir = (Compile / sourceDirectory).value / "scala" / "io" / "cloud4s" / "cli" / "bindings",
      cDir = (Compile / resourceDirectory).value / "scala-native" / "ssh"
    ),

    // 3. Definição segura das bindings do Bindgen
    bindgenBindings += {
      val headerFile = file("/usr/include/libssh/libssh.h")

      Binding(headerFile, "ssh")
        .withLinkName("ssh")
        .withCImports(List("libssh/libssh.h"))
        .withClangFlags(List("-I/usr/include", "-std=gnu99"))
        .withNoLocation(true)
    },

    // 4. Configuração unificada do Scala Native
    nativeConfig := {
      val conf = nativeConfig.value

      // Validação de plataforma para Apple Silicon (macOS arm64)
      conf
        .withLinkingOptions(
          conf.linkingOptions ++ Seq("-lcurl", "-lssh", "-lssl", "-lcrypto", "-lstdc++")
        )
        .withLTO(LTO.none)
        .withMode(Mode.debug)
        .withGC(GC.immix)
        .withSourceLevelDebuggingConfig(_.enableAll)
        .withIncrementalCompilation(true)
        .withOptimize(false)
    },

    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v")
  )

commands += Command.command("release") { state =>
  println("Iniciando build de produção (Release)...")

  // 1. Modifica a configuração para produção temporariamente
  val stateWithConfig = Project.extract(state).appendWithoutSession(Seq(
    Compile / nativeConfig ~= { _
      .withMode(scala.scalanative.build.Mode.releaseFast)
      .withLTO(scala.scalanative.build.LTO.thin)
      .withGC(scala.scalanative.build.GC.commix)
      .withOptimize(true)
    }
  ), state)

  // 2. Executa o build e pega o caminho do binário gerado
  val (nextState, artifactFile) = Project.extract(stateWithConfig).runTask(Compile / nativeLink, stateWithConfig)

  // 3. Define a pasta de destino (dist/) e o nome do arquivo final
  val destFile = baseDirectory.value / "dist" / artifactFile.getName

  println(s"Copiando executável final para: ${destFile.getAbsolutePath}")
  IO.copyFile(artifactFile, destFile)

  println("Build de produção concluído com sucesso!")
  nextState
}