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
version := "0.0.3"
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
  .enablePlugins(ScalaNativePlugin, BindgenPlugin, VcpkgNativePlugin)
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

    // 1. Dependências C gerenciadas pelo vcpkg
    vcpkgDependencies := VcpkgDependencies("libssh", "openssl", "zlib"),

    // 2. Modos e caminhos de saída do Bindgen
    bindgenMode := BindgenMode.Manual(
      scalaDir = (Compile / sourceDirectory).value / "scala" / "io" / "cloud4s" / "cli" / "bindings",
      cDir = (Compile / resourceDirectory).value / "scala-native" / "ssh"
    ),

    // 3. Definição segura das bindings do Bindgen
    bindgenBindings += {
      val vcpkg = vcpkgConfigurator.value
      // Obtém os diretórios de include para a libssh
      val includes = vcpkg.includes("libssh")
      val includeFlags = vcpkg.pkgConfig.compilationFlags("libssh").toList

      // Encontra o cabeçalho libssh.h
      val headerFile = includes / "libssh" / "libssh.h"

      Binding(headerFile, "ssh")
        .withLinkName("ssh")
        .withCImports(List("libssh/libssh.h"))
        .withClangFlags(includeFlags ++ List("-std=gnu99"))
        .withNoLocation(true)
    },

    // 4. Configuração unificada do Scala Native
    nativeConfig := {
      val conf = nativeConfig.value
      val vcpkg = vcpkgConfigurator.value

      // Resgata as cflags e lflags para todos os pacotes vcpkg declarados
      val pkgs = Seq("libssh", "openssl", "zlib")
      val cflags = vcpkg.pkgConfig.compilationFlags(pkgs: _*).toList
      val lflags = vcpkg.pkgConfig.linkingFlags(pkgs: _*).toList

      // Validação de plataforma para Apple Silicon (macOS arm64)
      val isMac = System.getProperty("os.name").toLowerCase.contains("mac")
      val isArm64 = Platform.arch == Platform.Arch.Arm && Platform.bits == Platform.Bits.x64
      val macArmFlags = if (isMac && isArm64) List("-arch", "arm64") else Nil
      conf
        .withCompileOptions(conf.compileOptions ++ cflags ++ macArmFlags ++ Seq("-g"))
        .withLinkingOptions(conf.linkingOptions ++ lflags ++ macArmFlags ++ Seq("-lstdc++"))
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

/*
lazy val root = project
  .enablePlugins(ScalaNativePlugin)
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      ///"ch.qos.logback" % "logback-classic" % "1.5.3",
      //"com.jcraft" % "jsch" % "0.1.55",
      "org.typelevel" %% "cats-effect" % "3.7.0",
      "com.monovore" %% "decline" % "2.6.2",
      "com.monovore" %% "decline-effect" % "2.6.2",
      "com.lihaoyi" %% "upickle" % "4.4.3",
      "br.com.mobilemind" %% "sg4s-lib" % "0.1.0-SNAPSHOT",
      "org.scalameta" %% "munit" % "1.3.5" % Test
    ),
    nativeConfig := {
      val logger: TaskStreams = streams.value
      val targetName = s"${name.value}-assembly-${version.value}.jar"
      val target =
        new File(new File("target", s"scala-${scalaVersion.value}"), targetName)

      val shell: Seq[String] =
        if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c")
        else Seq("bash", "-c")
      val cmd = shell ++ Seq(
        "java",
        s"-agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/${organization.value}",
        "-jar",
        target.getAbsolutePath
      )
      val result = (cmd !)
      if (result == 0) {
        logger.log.success("image native config generate successful")
      } else {
        logger.log.success("image native config generate failure")
      }
    },
    nativeCompile := {
      val logger: TaskStreams = streams.value
      val targetName = s"${name.value}-assembly-${version.value}.jar"
      val target =
        new File(new File("target", s"scala-${scalaVersion.value}"), targetName)
      val executable = s"./target/${name.value}"
      if (!target.exists()) {
        logger.log.error("target not found. do you assembly?")
      } else {

        //val shell: Seq[String] = if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
        val cmd = Seq(
          "native-image",
          //"--static",
          "--verbose",
          "--allow-incomplete-classpath",
          "--report-unsupported-elements-at-runtime",
          "--no-fallback",
          "-jar",
          target.getAbsolutePath,
          executable
        )

        logger.log.info(s"execute: ${cmd.mkString(" ")}")

        val result = cmd ! logger.log
        if (result == 0) {
          logger.log.success(
            s"image native compile successful, executable at $executable"
          )
        } else {
          logger.log.success("image native compile failure")
        }
      }
    }
  )

(assembly / assemblyMergeStrategy) := {
  case "reference.conf" => MergeStrategy.concat
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}

nativeCompile := (nativeCompile dependsOn assembly).evaluated
*/