package io.cloud4s.cli

import cats.effect.*
import cats.implicits.*
import com.monovore.decline.*
import com.monovore.decline.effect.*
import AppCmds.*
import AppConfigs.Config

import java.nio.file.{Files, Path, StandardOpenOption}
import scala.language.postfixOps
import scala.sys.process.*
import upickle.implicits.key
import upickle.default.{macroRW, ReadWriter as RW}

import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import scala.scalanative.unsafe.Zone

object Cloud4s
    extends CommandIOApp(
      name = "cloud4s",
      header = "Mobile Mind Cloud CLI Tool",
      version = "0.0.3"
    ) {

  def managedZone: Resource[IO, Zone] =
    Resource.make(
      IO.delay(Zone.open()) // Acquire the Zone
    )(zone =>
      IO.delay(zone.close()) // Release the Zone safely when done
    )

  override def main: Opts[IO[ExitCode]] =
    cmds.map { cmd =>
      managedZone.use { zone =>
        runCmd(cmd)(using zone)
      }
    }

  def runCmd(cmd: Cmd): Zone ?=> IO[ExitCode] =
    for
      cfg <- AppConfigs.getConfigs()
      red <-
        given configs: Config = cfg
        cmd match
          case CodeBuildAppInc(alias)                => codebuild.inc(alias)
          case CodeBuildAppDec(alias)                => codebuild.dec(alias)
          case CodeBuildAppCurr(alias)               => codebuild.curr(alias)
          case CodeBuildAppInfo(alias)               => codebuild.info(alias)
          case CodeBuildUpdateAlias(alias, newAlias) =>
            codebuild.updateAlias(alias, newAlias)
          case CodeBuildUpdateCodeBuildProjectName(alias, newProjectName) =>
            codebuild.updateCbProjectName(alias, newProjectName)
          case CodeBuildAppList()    => codebuild.list()
          case cb: CodeBuildStart    => codebuild.start(cb)
          case cb: CodeBuildStop     => codebuild.stop(cb)
          case cb: CodeBuildStatus   => codebuild.status(cb)
          case cb: CodeBuildLogs     => codebuild.logs(cb)
          case cb: CodeBuildInfo     => codebuild.info(cb)
          case cb: CodeBuildProjects => codebuild.projects(cb)
          case StackDeploy(_)        => docker.runOnMainHost(cmd)
          case StackRemove(_)        => docker.runOnMainHost(cmd)
          case ServiceUpdate(_, _)   => docker.runOnMainHost(cmd)
          case ServicePS(_, _)       => docker.runOnMainHost(cmd)
          case ServiceStop(_)        => docker.runOnMainHost(cmd)
          case ServiceGetLogs(_)     =>
            docker.runOnMainHost(cmd, Some(docker.logAnalyzer))
          case ServiceList() => docker.runOnMainHost(cmd)
          case DockerPrune() => docker.runOnAllHosts(cmd)
          case DockerPS()    => docker.runOnAllHosts(cmd)
          case DockerDF()    => docker.runOnAllHosts(cmd)
          case DockerStats() => docker.runOnAllHosts(cmd)
    yield red
}
// https://ben.kirw.in/decline/usage.html

type IOResult = Zone ?=> Config ?=> IO[ExitCode]
type Analyzer = String => IOResult

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
val appName = "cloud4s"
val localApps = Path.of(System.getenv("HOME"), ".cloud4s-apps.json")

case class AppInfo(
    id: Int,
    alias: String,
    @key("version_tag")
    version: String,
    @key("aws_ecr_repository_name")
    ecrRepoName: String,
    @key("aws_code_build_project_name")
    codeBuildProjectName: String,
    @key("last_update")
    lastUpdate: String
)

case class AppVersion(
    @key("last_version") lastVersion: String,
    @key("curr_version") currVersion: String,
    @key("last_update") lastUpdate: String
)

given RW[AppInfo] = macroRW
given RW[AppVersion] = macroRW

def say(s: String): IO[Unit] =
  IO.blocking:
    Console.print(Console.GREEN)
    Console.print(
      s"\n[${LocalDateTime.now().format(formatter)}][INFO]::> \n$s\n"
    )
    Console.print(Console.RESET)

def sayError(s: String): IO[ExitCode] =
  IO.blocking:
    Console.print(Console.RED)
    Console.print(
      s"\n[${LocalDateTime.now().format(formatter)}][ERROR]::>\n\n$s\n"
    )
    Console.print(Console.RESET)
  *> IO.unit.as(ExitCode.Error)

def sayOk(s: String): IO[ExitCode] =
  say(s) *> IO.unit.as(ExitCode.Success)

def showLogs(out: String): IO[ExitCode] =
  IO.blocking {
    Console.print(Console.GREEN)
    Console.print(
      s"\n[${LocalDateTime.now().format(formatter)}][LOGS]::>\n\n"
    )
    Console.print(out)
    Console.print("\n\n")
    Console.print(Console.RESET)
  } *> IO.unit.as(ExitCode.Success)

object codebuild:

  def inc(alias: String): IOResult =
    cbCallApi(s"/api/app/version/increment/$alias")
      .flatMap { data =>
        val appVersion = upickle.read[AppVersion](data)
        printAppVersionTable(appVersion)
        IO.unit.as(ExitCode.Success)
      }

  def dec(alias: String): IOResult =
    cbCallApi(s"/api/app/version/decrement/$alias")
      .flatMap { data =>
        val appVersion = upickle.read[AppVersion](data)
        printAppVersionTable(appVersion)
        IO.unit.as(ExitCode.Success)
      }

  def curr(alias: String): IOResult =
    cbCallApi(s"/api/app/version/current/$alias")
      .flatMap { data =>
        val appVersion = upickle.read[AppVersion](data)
        printAppVersionTable(appVersion)
        IO.unit.as(ExitCode.Success)
      }

  def info(alias: String): IOResult =
    cbCallApi(s"/api/app/info/$alias")
      .flatMap { data =>
        val info = upickle.read[AppInfo](data)
        Console.print(Console.YELLOW)
        println("\n\n")
        println(s"               ID: ${info.id}")
        println(s"            Alias: ${info.alias}")
        println(s"          Version: ${info.version}")
        println(s"       LastUpdate: ${info.lastUpdate}")
        println(s"Codebuild Project: ${info.codeBuildProjectName}")
        println(s"      ECR Project: ${info.ecrRepoName}")
        println("\n\n")
        Console.print(Console.RESET)
        IO.unit.as(ExitCode.Success)
      }

  def updateAlias(alias: String, newAlias: String): IOResult =
    val cfg = summon[Config]
    val url = s"${cfg.codebuildUrl}/api/app/update/alias"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    val payload = Map(
      "from_alias" -> alias,
      "to_alias" -> newAlias
    )
    Http(url, Some(auth))
      .post(upickle.write(payload))
      .flatMap { resp =>
        say(resp.body) *>
          (resp.statusCode match
            case 200  => IO.unit.as(ExitCode.Success)
            case code =>
              IO.raiseError(
                new Exception(s"Server status code ${resp.statusCode}")
              ))
      }

  def updateCbProjectName(alias: String, newCbProjectName: String): IOResult =
    val cfg = summon[Config]
    val url = s"${cfg.codebuildUrl}/api/app/update/codebuild-project-name"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    val payload = Map(
      "alias" -> alias,
      "project_name" -> newCbProjectName
    )
    Http(url, Some(auth))
      .post(upickle.write(payload))
      .flatMap { resp =>
        say(resp.body) *>
          (resp.statusCode match
            case 200  => IO.unit.as(ExitCode.Success)
            case code =>
              IO.raiseError(
                new Exception(s"Server status code ${resp.statusCode}")
              ))
      }

  def list(): IOResult =
    fetchApps().map { apps =>
      printAppsTable(apps)
      ExitCode.Success
    }

  def printAppVersionTable(appVersion: AppVersion) =
    Console.print(Console.YELLOW)
    println("\n\n")
    println(s"Current Version: ${appVersion.currVersion}")
    if appVersion.currVersion != appVersion.lastVersion
    then println(s"   Last Version: ${appVersion.lastVersion}")
    println(s"    Last update: ${appVersion.lastUpdate}")
    println("\n\n")
    Console.print(Console.RESET)

  def printAppsTable(apps: Seq[AppInfo]) =
    val maxAlias = apps.map(_.alias.length).max
    val maxVersion = apps.map(_.version.length).max
    val maxCbProject = apps.map(_.codeBuildProjectName.length).max
    val maxEcr = apps.map(_.ecrRepoName.length).max
    val maxLastUpdate = apps.map(_.lastUpdate.length).max
    val fmt =
      s"%-${maxAlias}s | %-${maxVersion}s | %-${maxCbProject}s | %-${maxEcr}s | %-${maxLastUpdate}s"
    Console.print(Console.YELLOW)
    println("\n\n")
    println(
      fmt.formatted(
        "Alias",
        "Version",
        "CodeBuild Project",
        "ECR Repo Name",
        "Last Update"
      )
    )
    for app <- apps do
      println(
        fmt.formatted(
          app.alias,
          app.version,
          app.codeBuildProjectName,
          app.ecrRepoName,
          app.lastUpdate
        )
      )
    println("\n\n")
    Console.print(Console.RESET)

  private def cbCallApi(path: String)(using cfg: Config): IO[ujson.Value] =
    val url = s"${cfg.codebuildUrl}$path"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    Http(url, Some(auth)).get
      .flatMap: resp =>
        resp.statusCode match
          case 200  => IO.blocking { ujson.read(resp.body) }
          case code =>
            say(resp.body) *> IO.raiseError(
              new Exception(s"Server status code ${resp.statusCode}")
            )

  private def getBuildId(cb: CodeBuildShowBuildId): Config ?=> IO[String] =
    findAppByAlias(cb.alias)
      .flatMap { app =>
        IO.blocking:
          val filterCmd = "jq -r '.ids[0]'"
          (cb.cmd.replace(
            "__project__name__",
            app.codeBuildProjectName
          ) #| filterCmd) !!
      }

  def start(cb: CodeBuildStart): IOResult =
    findAppByAlias(cb.alias)
      .flatMap { app =>
        IO.blocking(
          (cb.cmd.replace("__project__name__", app.codeBuildProjectName) !!)
        ).flatMap(sayOk)
      }

  def fetchApps(): Config ?=> IO[Seq[AppInfo]] =
    val cfg = summon[Config]
    val url = s"${cfg.codebuildUrl}/api/app/list"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    Http(url, Some(auth)).get
      .flatMap { resp =>
        IO.blocking {
          val content = resp.body
          Files.write(
            localApps,
            content.getBytes(StandardCharsets.UTF_8),
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING
          )
          upickle.read[Seq[AppInfo]](content)
        }
      }

  def loadApps(): Config ?=> IO[Seq[AppInfo]] =
    if localApps.toFile.exists()
    then
      IO.blocking {
        upickle.read[Seq[AppInfo]](Files.readString(localApps))
      }
    else fetchApps()

  def findAppByAlias(alias: String): Config ?=> IO[AppInfo] =
    loadApps().flatMap: apps =>
      apps.find(_.alias == alias) match
        case Some(app) => IO.pure(app)
        case None      =>
          IO.raiseError(new Exception(s"app not found for alias: $alias"))

  def status(cb: CodeBuildStatus): IOResult =
    findAppByAlias(cb.alias).flatMap: app =>
      getBuildId(CodeBuildShowBuildId(cb.alias))
        .flatMap: bid =>
          if bid.isEmpty
          then sayOk(s"build not found to project ${app.codeBuildProjectName}")
          else
            IO.blocking:
              val cmd = cb.cmd.replace("__build_id__", bid)
              val filterCmd =
                "jq '.builds[].phases[] | select (.phaseType==\"BUILD\") | .phaseStatus'"
              (cmd #| filterCmd) !!
            .flatMap: r =>
                sayOk(s"build status: ${
                    if r == "null" || r.isEmpty then "BUILDING" else r
                  }")

  private def getLogInfo(bid: String): IO[Either[String, (String, String)]] =
    IO.blocking:

      val cmd = CodeBuildStatus("").cmd.replace("__build_id__", bid)
      val filterCmd =
        "jq '.builds[0].logs.groupName,.builds[0].logs.streamName'"
      ((cmd #| filterCmd) !!)
    .map: r =>
        if r == "null"
        then Left("cannot get logs")
        else {
          r.split("\n").toList match
            case first :: second :: Nil => Right((first, second))
            case _                      => Left("cannot parse log name")
        }

  def logs(cb: CodeBuildLogs): IOResult =
    findAppByAlias(cb.alias).flatMap: app =>
      getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
        .flatMap: bid =>
          if bid.isEmpty
          then sayOk(s"build not found to project ${app.codeBuildProjectName}")
          else
            getLogInfo(bid)
              .flatMap {
                case Left(msg)                      => sayOk(msg)
                case Right((groupName, streamName)) =>
                  IO.blocking {
                    val cmd = cb.cmd
                      .replace("__group_name__", groupName)
                      .replace("__stream_name__", streamName)
                    val filterCmd = "jq -r '.events[].message'"
                    ((cmd #| filterCmd) !!)
                  }.map(
                    _.split("\n").toList
                      .filter(_.trim().nonEmpty)
                      .mkString("\n")
                  ).flatMap(showLogs)
              }

  def info(cb: CodeBuildInfo): IOResult =
    findAppByAlias(cb.alias).flatMap: app =>
      getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
        .flatMap: bid =>
          if bid.isEmpty
          then sayOk(s"build not found to project ${app.codeBuildProjectName}")
          else
            IO.blocking:
              cb.cmd.replace("__build_id__", bid) !!
            .flatMap: r =>
                sayOk(s"build info:\n\n$r")

  def stop(cb: CodeBuildStop): IOResult =
    findAppByAlias(cb.alias).flatMap: app =>
      getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
        .flatMap: bid =>
          if bid.isEmpty
          then sayOk(s"build not found to project ${app.codeBuildProjectName}")
          else
            IO.blocking:
              cb.cmd.replace("__build_id__", bid) !!
            .flatMap(sayOk)

  def projects(cb: CodeBuildProjects): IOResult =
    IO.blocking((cb.cmd !!))
      .flatMap: r =>
        sayOk(s"projects:\n\n$r")

object docker:

  def runOnMainHost(cmd: Cmd, analyzer: Option[Analyzer] = None): IOResult =
    val cfg = summon[Config]
    exec(cmd.cmd, analyzer, cfg.hostMain)

  def runOnAllHosts(cmd: Cmd): IOResult =
    val cfg = summon[Config]
    exec(cmd.cmd, None, cfg.hosts*)

  private def exec(
      cmd: String,
      analyzer: Option[Analyzer],
      hosts: String*
  ): IOResult =
    hosts
      .map: host =>
        runEach(cmd, host, analyzer)
      .parSequence
      .flatMap: codes =>
        IO.pure:
          codes
            .find(_ != ExitCode.Success)
            .getOrElse(ExitCode.Success)

  private def runEach(
      cmd: String,
      host: String,
      analyzer: Option[Analyzer] = None
  ): IOResult =
    Ssh
      .connectAndExec(cmd, host)
      .flatMap { content =>
        analyzer match
          case Some(f) => f(content)
          case None    =>
            sayOk(content)
      }

  def logAnalyzer(content: String): IOResult =
    // val str = "CONTAINER_NAME = 4gym_4gymIDS= cf2394pndkqxDONE! save log at logs/4gym-20260811172206.log"
    val RegexLog = """/([^/\s]+\.log)""".r
    val found = RegexLog.findFirstMatchIn(content).map(_.group(1))
    say(content) *>
      (found match
        case Some(fileName) => scpLog(fileName)
        case None => sayError("cannot get filename from server response"))

  def scpLog(filename: String): IOResult =
    val cfg = summon[Config]
    val to = s"${cfg.logsPath}/${filename}"
    val from =
      s"${cfg.hostMain}:~/cluster/logs/${filename}"
    val scp = Seq("scp", from, to)
    IO.blocking:
      scp ! ProcessLogger(s => println(s"SCP: $s"))
    .flatMap: code =>
        if code != 0
        then sayError("cannot save log")
        else sayOk(s"save log file in: $to")
