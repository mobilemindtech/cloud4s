package io.cloud4s.cli

import CliArgs.*
import AppConfigs.Config

import java.nio.file.{Files, Path, StandardOpenOption}
import scala.language.postfixOps
import scala.sys.process.*

import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import scala.scalanative.unsafe.Zone
import scala.util.{Try, Failure, Success}
import data.{*, given}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.*

type IOResult = Zone ?=> Config ?=> Try[Unit]
type Analyzer = String => IOResult

extension [T](x: T) def unit: Unit = ()

object Config:
  val appName = "cloud4s"
  val localApps = Path.of(System.getenv("HOME"), ".cloud4s-apps.json")

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

object Printer:
  def app(appVersion: AppVersion) =
    Console.print(Console.YELLOW)
    print(s"::> current version is ${appVersion.currVersion}")
    if appVersion.currVersion != appVersion.lastVersion
    then print(s", last version is ${appVersion.lastVersion}")
    print(s", last update is ${appVersion.lastUpdate}\n")
    Console.print(Console.RESET)

  def apps(apps: Seq[AppInfo]) =
    val maxAlias = apps.map(_.alias.length).max
    val maxVersion = 10
    val maxCbProject = apps.map(_.codeBuildProjectName.length).max
    val maxEcr = apps.map(_.ecrRepoName.length).max
    val maxLastUpdate = apps.map(_.lastUpdate.length).max
    val fmt =
      s"%-${maxAlias}s | %-${maxVersion}s | %-${maxCbProject}s | %-${maxEcr}s | %-${maxLastUpdate}s"
    Console.print(Console.YELLOW)
    println(
      fmt.formatted(
        "Alias",
        "Version",
        "CodeBuild Project",
        "ECR Repo Name",
        "Last Update"
      )
    )
    println("")
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
    Console.print(Console.RESET)

  def info(info: AppInfo) =
    Console.print(Console.YELLOW)
    println(s"::>                ID: ${info.id}")
    println(s"::>             Alias: ${info.alias}")
    println(s"::>           Version: ${info.version}")
    println(s"::>        LastUpdate: ${info.lastUpdate}")
    println(s"::> Codebuild Project: ${info.codeBuildProjectName}")
    println(s"::>       ECR Project: ${info.ecrRepoName}")
    Console.print(Console.RESET)

@main def main(args: String*): Unit =
  CliArgs.parse(args.toArray) match
    case Some(cmd) =>
      Zone:
        Cloud4s.runCmd(cmd) match
          case Success(_)  => ()
          case Failure(ex) =>
            System.getenv("ENVIRONMENT") match
              case "development" =>
                ex.printStackTrace()
              case _ => ()
    case None =>
      println("use --help")

object Cloud4s:
  def runCmd(cmd: Cmd): Zone ?=> Try[Unit] =
    for
      cfg <- AppConfigs.getConfigs()
      red <-
        given configs: Config = cfg
        cmd match
          case CodeBuildAppInc(alias)                => api.inc(alias)
          case CodeBuildAppDec(alias)                => api.dec(alias)
          case CodeBuildAppCurr(alias)               => api.curr(alias)
          case CodeBuildAppInfo(alias)               => api.info(alias)
          case CodeBuildUpdateAlias(alias, newAlias) =>
            api.updateAlias(alias, newAlias)
          case CodeBuildUpdateCodeBuildProjectName(alias, newProjectName) =>
            api.updateCbProjectName(alias, newProjectName)
          case CodeBuildAppList()    => api.list()
          case cb: CodeBuildStart    => aws.start(cb)
          case cb: CodeBuildStop     => aws.stop(cb)
          case cb: CodeBuildStatus   => aws.status(cb)
          case cb: CodeBuildLogs     => aws.logs(cb)
          case cb: CodeBuildInfo     => aws.info(cb)
          case cb: CodeBuildProjects => aws.projects(cb)
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

object api:

  def inc(alias: String): IOResult =
    cbCallApi(s"/api/app/version/increment/$alias")
      .map: data =>
        val appVersion = upickle.read[AppVersion](data)
        Printer.app(appVersion)

  def dec(alias: String): IOResult =
    cbCallApi(s"/api/app/version/decrement/$alias")
      .map: data =>
        val appVersion = upickle.read[AppVersion](data)
        Printer.app(appVersion)

  def curr(alias: String): IOResult =
    cbCallApi(s"/api/app/version/current/$alias")
      .map: data =>
        val appVersion = upickle.read[AppVersion](data)
        Printer.app(appVersion)

  def info(alias: String): IOResult =
    cbCallApi(s"/api/app/info/$alias")
      .map: data =>
        val info = upickle.read[AppInfo](data)
        Printer.info(info)

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
      .map: resp =>
        Logger.info(resp.body)
        if resp.statusCode != 200
        then throw new Exception(s"Server status code ${resp.statusCode}")

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
      .map: resp =>
        Logger.info(resp.body)
        if resp.statusCode != 200
        then throw new Exception(s"Server status code ${resp.statusCode}")

  def list(): IOResult =
    fetchApps()
      .map: apps =>
        Printer.apps(apps)

  private def cbCallApi(path: String)(using cfg: Config): Try[ujson.Value] =
    val url = s"${cfg.codebuildUrl}$path"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    Http(url, Some(auth)).get
      .map: resp =>
        resp.statusCode match
          case 200 => ujson.read(resp.body)
          case _   =>
            Logger.error(resp.body)
            throw new Exception(s"Server status code ${resp.statusCode}")

  def fetchApps(): Config ?=> Try[Seq[AppInfo]] =
    val cfg = summon[Config]
    val url = s"${cfg.codebuildUrl}/api/app/list"
    val auth = AuthBasic(cfg.codebuildUsername, cfg.codebuildPassword)
    Http(url, Some(auth)).get
      .map: resp =>
        val content = resp.body
        Files.write(
          Config.localApps,
          content.getBytes(StandardCharsets.UTF_8),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING
        )
        upickle.read[Seq[AppInfo]](content)

  def loadApps(): Config ?=> Try[Seq[AppInfo]] =
    if Config.localApps.toFile.exists()
    then
      Try:
        upickle.read[Seq[AppInfo]](Files.readString(Config.localApps))
    else fetchApps()

  def findAppByAlias(alias: String): Config ?=> Try[AppInfo] =
    loadApps()
      .map: apps =>
        apps.find(_.alias == alias) match
          case Some(app) => app
          case None      =>
            throw new Exception(s"app not found for alias: $alias")

object aws:

  private def getBuildId(cb: CodeBuildShowBuildId): Config ?=> Try[String] =
    api
      .findAppByAlias(cb.alias)
      .map: app =>
        val filterCmd = "jq -r '.ids[0]'"
        (cb.cmd.replace(
          "__project__name__",
          app.codeBuildProjectName
        ) #| filterCmd) !!

  def start(cb: CodeBuildStart): IOResult =
    api
      .findAppByAlias(cb.alias)
      .map: app =>
        val result =
          (cb.cmd.replace("__project__name__", app.codeBuildProjectName) !!)
        Logger.info(result)

  def status(cb: CodeBuildStatus): IOResult =
    api
      .findAppByAlias(cb.alias)
      .flatMap: app =>
        getBuildId(CodeBuildShowBuildId(cb.alias))
          .map: bid =>
            if bid.isEmpty
            then
              Logger.error(
                s"build not found to project ${app.codeBuildProjectName}"
              )
            else
              val cmd = cb.cmd.replace("__build_id__", bid)
              val filterCmd =
                "jq '.builds[].phases[] | select (.phaseType==\"BUILD\") | .phaseStatus'"
              val result: String = (cmd #| filterCmd) !!
              val r =
                if result == "null" || result.isEmpty
                then "BUILDING"
                else result
              Logger.info(s"build status: $r")

  private def getLogInfo(bid: String): Try[Either[String, (String, String)]] =
    Try:
      val cmd = CodeBuildStatus("").cmd.replace("__build_id__", bid)
      val filterCmd =
        "jq '.builds[0].logs.groupName,.builds[0].logs.streamName'"
      val result = ((cmd #| filterCmd) !!)
      if result == "null"
      then Left("cannot get logs")
      else
        result.split("\n").toList match
          case first :: second :: Nil => Right((first, second))
          case _                      => Left("cannot parse log name")

  def logs(cb: CodeBuildLogs): IOResult =
    api
      .findAppByAlias(cb.alias)
      .flatMap: app =>
        getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
          .flatMap: bid =>
            if bid.isEmpty
            then
              Logger.info(
                s"build not found to project ${app.codeBuildProjectName}"
              )
              Try(())
            else
              getLogInfo(bid)
                .map:
                  case Left(msg) =>
                    Logger.info(msg)
                  case Right((groupName, streamName)) =>
                    val cmd = cb.cmd
                      .replace("__group_name__", groupName)
                      .replace("__stream_name__", streamName)
                    val filterCmd = "jq -r '.events[].message'"
                    val result = ((cmd #| filterCmd) !!)
                    val s = result
                      .split("\n")
                      .toList
                      .filter(_.trim().nonEmpty)
                      .mkString("\n")
                    Logger.info(s)

  def info(cb: CodeBuildInfo): IOResult =
    api
      .findAppByAlias(cb.alias)
      .flatMap: app =>
        getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
          .map: bid =>
            if bid.isEmpty
            then
              Logger.info(
                s"build not found to project ${app.codeBuildProjectName}"
              )
            else
              val result: String = (cb.cmd.replace("__build_id__", bid) !!)
              Logger.info(s"build info:\n\n$result")

  def stop(cb: CodeBuildStop): IOResult =
    api
      .findAppByAlias(cb.alias)
      .flatMap: app =>
        getBuildId(CodeBuildShowBuildId(app.codeBuildProjectName))
          .map: bid =>
            if bid.isEmpty
            then
              Logger.info(
                s"build not found to project ${app.codeBuildProjectName}"
              )
            else
              val result = (cb.cmd.replace("__build_id__", bid) !!)
              Logger.info(result)

  def projects(cb: CodeBuildProjects): IOResult =
    Try:
      val r = (cb.cmd !!)
      Logger.info(s"projects:\n\n$r")

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
    val futures =
      hosts
        .map: host =>
          Future[Try[Unit]]:
            Zone:
              given cfg: Config = summon[Config]
              runEach(cmd, host, analyzer)

    for future <- futures do Await.result(future, 5.seconds)

    Try(())

  private def runEach(
      cmd: String,
      host: String,
      analyzer: Option[Analyzer] = None
  ): IOResult =
    Ssh
      .connectAndExec(cmd, host)
      .flatMap: content =>
        analyzer match
          case Some(f) => f(content)
          case None    =>
            Try(Logger.info(content))

  def logAnalyzer(content: String): IOResult =
    // val str = "CONTAINER_NAME = 4gym_4gymIDS= cf2394pndkqxDONE! save log at logs/4gym-20260811172206.log"
    Try:
      val RegexLog = """/([^/\s]+\.log)""".r
      val found = RegexLog.findFirstMatchIn(content).map(_.group(1))
      Logger.info(content)
      found match
        case Some(fileName) =>
          scpLog(fileName) match
            case Failure(ex) => throw ex
            case Success(_)  => ()
        case None =>
          throw new Exception("cannot get filename from server response")

  def scpLog(filename: String): IOResult =
    val cfg = summon[Config]
    val to = s"${cfg.logsPath}/${filename}"
    val from =
      s"${cfg.hostMain}:~/cluster/logs/${filename}"
    val scp = Seq("scp", from, to)
    Try:
      scp ! ProcessLogger(s => println(s"SCP: $s"))
    .map: code =>
      if code != 0
      then Logger.error("cannot save log")
      else Logger.info(s"save log file in: $to")
