package io.cloud4s.cli

import com.monovore.decline.Opts
import cats.implicits.*

object AppCmds:

  trait Cmd:
    def cmd: String

  def runOnCluster(cmd: String) = s"cd ./cluster && ./docker $cmd"

  case class StackDeploy(stack: String) extends Cmd:
    def cmd = runOnCluster(s"deploy $stack")

  case class ServiceUpdate(stack: String, service: String) extends Cmd:
    def cmd = s"docker service update --force ${stack}_$service"

  case class ServicePS(service: String, running: Boolean) extends Cmd:
    def cmd =
      if running
      then runOnCluster(s"ps $service | grep Running")
      else runOnCluster(s"ps $service")

  case class ServiceStop(service: String) extends Cmd:
    def cmd = runOnCluster(s"stop $service")

  case class ServiceList() extends Cmd:
    def cmd = runOnCluster("ls")

  case class ServiceGetLogs(service: String) extends Cmd:
    def cmd = runOnCluster(s"getlogs $service")

  case class StackRemove(stack: String) extends Cmd:
    def cmd = runOnCluster(s"rm $stack")

  case class DockerPrune() extends Cmd:
    def cmd = "docker system prune -a -f"

  case class DockerPS() extends Cmd:
    def cmd = "docker ps"

  case class DockerDF() extends Cmd:
    def cmd = "docker df"

  case class DockerStats() extends Cmd:
    def cmd = "docker stats --no-stream --no-trunc"

  trait CodeBuild extends Cmd:
    def cmd = ""
  case class CodeBuildAppInc(alias: String) extends CodeBuild
  case class CodeBuildAppDec(alias: String) extends CodeBuild
  case class CodeBuildAppInfo(alias: String) extends CodeBuild
  case class CodeBuildAppList() extends CodeBuild
  case class CodeBuildAppCurr(alias: String) extends CodeBuild
  case class CodeBuildUpdateAlias(fromAlias: String, toAlias: String)
      extends CodeBuild
  case class CodeBuildUpdateCodeBuildProjectName(
      alias: String,
      newProjectName: String
  ) extends CodeBuild
  case class CodeBuildStart(alias: String) extends CodeBuild:
    override def cmd =
      s"aws codebuild start-build --project-name __project__name__"
  case class CodeBuildShowBuildId(alias: String) extends CodeBuild:
    override def cmd =
      s"aws codebuild list-builds-for-project --project-name __project__name__"
  case class CodeBuildStop(alias: String) extends CodeBuild:
    override def cmd = s"aws codebuild stop-build --id __build_id__"
  case class CodeBuildInfo(alias: String) extends CodeBuild:
    override def cmd = s"aws codebuild batch-get-builds --ids __build_id__"
  case class CodeBuildStatus(alias: String) extends CodeBuild:
    override def cmd = s"aws codebuild batch-get-builds --ids __build_id__"
  case class CodeBuildLogs(alias: String) extends CodeBuild:
    override def cmd =
      s"aws logs get-log-events --log-group-name __group_name__ --log-stream-name __stream_name__"
  case class CodeBuildProjects() extends CodeBuild:
    override def cmd = s"aws codebuild list-projects"

  val codebuildAppInc: Opts[CodeBuild] =
    Opts.subcommand("inc", "Increment service version") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildAppInc.apply)
    }

  val codebuildAppDec: Opts[CodeBuild] =
    Opts.subcommand("dec", "Decrement service version") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildAppDec.apply)
    }

  val codebuildAppCurr: Opts[CodeBuild] =
    Opts.subcommand("version", "Show current service version") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildAppCurr.apply)
    }

  val codebuildUpdateAlias: Opts[CodeBuild] =
    Opts.subcommand("alias", "Update app alias") {
      (
        Opts.argument[String](metavar = "Current alias"),
        Opts.argument[String](metavar = "New alias")
      )
        .mapN(CodeBuildUpdateAlias.apply)
    }

  val codeBuildUpdateCodeBuildProjectName: Opts[CodeBuild] =
    Opts.subcommand("project", "Update code build project name") {
      (
        Opts.argument[String](metavar = "Current alias"),
        Opts.argument[String](metavar = "New project name")
      )
        .mapN(CodeBuildUpdateCodeBuildProjectName.apply)
    }

  val codebuildAppInfo: Opts[CodeBuild] =
    Opts.subcommand("info", "Show app info") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildAppInfo.apply)
    }

  val codebuildAppList: Opts[CodeBuild] =
    Opts.subcommand("apps", "Show app list") {
      Opts(CodeBuildAppList())
    }

  val codebuildStart: Opts[CodeBuild] =
    Opts.subcommand("start", "Start build on AWS CodeBuild") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildStart.apply)
    }

  val codebuildStop: Opts[CodeBuild] =
    Opts.subcommand("stop", "Stop build on AWS CodeBuild") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildStop.apply)
    }

  val codebuildInfo: Opts[CodeBuild] =
    Opts.subcommand("info", "Show build info from AWS CodeBuild") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildInfo.apply)
    }

  val codebuildStatus: Opts[CodeBuild] =
    Opts.subcommand("status", "Show build status from AWS CodeBuild") {
      Opts
        .argument[String](metavar = "App alias")
        .map(CodeBuildStatus.apply)
    }

  val codebuildLogs: Opts[CodeBuild] =
    Opts.subcommand("logs", "Show logs for last build from AWS CodeBuild") {
      Opts
        .argument[String](metavar = "AWS CodeBuild project name")
        .map(CodeBuildLogs.apply)
    }

  val codebuildList: Opts[CodeBuild] =
    Opts.subcommand("projects", "List all projects on AWS CodeBuild") {
      Opts(CodeBuildProjects())
    }

  val codebuildUpdate: Opts[CodeBuild] =
    Opts.subcommand("update", "API Update Commands") {
      (codebuildUpdateAlias orElse codeBuildUpdateCodeBuildProjectName)
    }

  val stackDeploy: Opts[StackDeploy] =
    Opts.subcommand("deploy", "Swarm stack deploy") {
      Opts.argument[String](metavar = "stack name").map(StackDeploy.apply)
    }

  val stackRemove: Opts[StackRemove] =
    Opts.subcommand("rm", "Swarm stack remove") {
      Opts.argument[String](metavar = "stack name").map(StackRemove.apply)
    }

  val serviceStop: Opts[ServiceStop] =
    Opts.subcommand("stop", "Swarm service remove") {
      Opts.argument[String](metavar = "service name").map(ServiceStop.apply)
    }

  val serviceUpdate: Opts[ServiceUpdate] =
    Opts.subcommand("update", "Swarm force service update") {
      (
        Opts.argument[String](metavar = "stack name"),
        Opts.argument[String](metavar = "service name")
      ).mapN(ServiceUpdate.apply)
    }

  val servicePS: Opts[ServicePS] =
    Opts.subcommand("ps", "Swarm show service info") {
      (
        Opts.argument[String](metavar = "service name"),
        Opts.flag("running", "filter by running services").orFalse
      ).mapN(ServicePS.apply)
    }

  val serviceLS: Opts[ServiceList] =
    Opts.subcommand("ls", "Swarm list all services") {
      Opts(ServiceList())
    }

  // val serviceRM: Opts[ServiceStop] =
  //  Opts.subcommand("stop", "Swarm stop service") {
  //    Opts.argument[String](metavar = "service name").map(ServiceStop.apply)
  //  }

  val serviceGetLogs: Opts[ServiceGetLogs] =
    Opts.subcommand("logs", "Swarm get service logs") {
      Opts.argument[String](metavar = "service name").map(ServiceGetLogs.apply)
    }

  val dockerPrune: Opts[DockerPrune] =
    Opts.subcommand("prune", "Docker remove unused data") {
      Opts(DockerPrune())
    }

  val dockerPS: Opts[DockerPS] =
    Opts.subcommand("ps", "Docker lists containers") {
      Opts(DockerPS())
    }

  val dockerDF: Opts[DockerDF] =
    Opts.subcommand("df", "Docker file system space usage") {
      Opts(DockerDF())
    }

  val dockerStats: Opts[DockerStats] =
    Opts.subcommand(
      "stats",
      "Docker containers usage statistics"
    ) {
      Opts(DockerStats())
    }

  val codebuildAws: Opts[CodeBuild] =
    Opts.subcommand("aws", "AWS Actions") {
      (codebuildAppInc
        orElse codebuildStart
        orElse codebuildStop
        orElse codebuildInfo
        orElse codebuildStatus
        orElse codebuildLogs
        orElse codebuildList)
    }

  val codebuildApi: Opts[CodeBuild] =
    Opts.subcommand("api", "API Commands") {
      (codebuildAppInc
        orElse codebuildAppDec
        orElse codebuildAppCurr
        orElse codebuildAppInfo
        orElse codebuildAppList
        orElse codebuildUpdate)
    }

  val cmdSwarmCommands: Opts[Cmd] =
    Opts.subcommand("swarm", "Swarm Commands") {
      (stackDeploy
        orElse stackRemove
        orElse serviceStop
        orElse serviceUpdate
        orElse servicePS
        orElse serviceLS
        orElse serviceGetLogs)
    }

  val cmdDockerCommands: Opts[Cmd] =
    Opts.subcommand("docker", "Docker Commands") {
      (dockerPrune
        orElse dockerPS
        orElse dockerDF
        orElse dockerStats)
    }

  val cmds = (cmdSwarmCommands
    orElse cmdDockerCommands
    orElse codebuildAws
    orElse codebuildApi)
