package io.cloud4s.cli

import scopt.OParser

object CliArgs:

  def runOnCluster(cmd: String) = s"cd ./cluster && ./docker $cmd"

  trait Cmd:
    def cmd: String

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

  case class ServiceGetLogs(
      service: String,
      follow: Boolean = false,
      tail: Int = -1
  ) extends Cmd:
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
  case class CodeBuildLogs(alias: String, follow: Boolean = false)
      extends CodeBuild:
    override def cmd =
      s"aws logs get-log-events --log-group-name __group_name__ --log-stream-name __stream_name__"

    def cmdStream =
      """
      | aws logs tail "/aws/codebuild/__project__name__"
      | --log-stream-names __stream_name__
      | --follow
      """.stripMargin

  case class CodeBuildProjects() extends CodeBuild:
    override def cmd = s"aws codebuild list-projects"

  case class Config(cmd: Option[Cmd] = None)

  private val builder = OParser.builder[Config]
  import builder.*

  val parser: OParser[Unit, Config] =
    OParser.sequence(
      programName("cloud4s"),
      head("cloud4s", "1.0"),

      // --- Swarm Commands ---
      cmd("swarm")
        .text("Swarm Commands")
        .children(
          cmd("deploy")
            .text("Swarm stack deploy")
            .children(
              arg[String]("<stack name>")
                .text("stack name")
                .action((x, c) => c.copy(cmd = Some(StackDeploy(x))))
            ),
          cmd("rm")
            .text("Swarm stack remove")
            .children(
              arg[String]("<stack name>")
                .text("stack name")
                .action((x, c) => c.copy(cmd = Some(StackRemove(x))))
            ),
          cmd("stop")
            .text("Swarm service remove")
            .children(
              arg[String]("<service name>")
                .text("service name")
                .action((x, c) => c.copy(cmd = Some(ServiceStop(x))))
            ),
          cmd("update")
            .text("Swarm force service update")
            .action((_, c) => c.copy(cmd = Some(ServiceUpdate("", ""))))
            .children(
              arg[String]("<stack name>")
                .text("stack name")
                .action((x, c) =>
                  c.copy(cmd = c.cmd.collect { case su: ServiceUpdate =>
                    su.copy(stack = x)
                  })
                ),
              arg[String]("<service name>")
                .text("service name")
                .action((x, c) =>
                  c.copy(cmd = c.cmd.collect { case su: ServiceUpdate =>
                    su.copy(service = x)
                  })
                )
            ),
          cmd("ps")
            .text("Swarm show service info")
            .action((_, c) =>
              c.copy(cmd = Some(ServicePS("", running = false)))
            )
            .children(
              arg[String]("<service name>")
                .text("service name")
                .action((x, c) =>
                  c.copy(cmd = c.cmd.collect { case ps: ServicePS =>
                    ps.copy(service = x)
                  })
                ),
              opt[Unit]('r', "running")
                .text("filter by running services")
                .action((_, c) =>
                  c.copy(cmd = c.cmd.collect { case ps: ServicePS =>
                    ps.copy(running = true)
                  })
                )
            ),
          cmd("ls")
            .text("Swarm list all services")
            .action((_, c) => c.copy(cmd = Some(ServiceList()))),
          cmd("logs")
            .text("Swarm get service logs")
            .children(
              arg[String]("<service name>")
                .text("service name")
                .action((x, c) => c.copy(cmd = Some(ServiceGetLogs(x)))),
              opt[Unit]('f', "follow")
                .text("Follow log output")
                .action((_, c) =>
                  c.copy(cmd = c.cmd.collect { case it: ServiceGetLogs =>
                    it.copy(follow = true)
                  })
                ),
              opt[Unit]('t', "tail")
                .text("number of lines to show from the end of the logs")
                .children(
                  arg[Int]("<number>")
                    .text("number of lines")
                    .action((n, c) =>
                      c.copy(cmd = c.cmd.collect { case it: ServiceGetLogs =>
                        it.copy(tail = n)
                      })
                    )
                )
            )
        ),

      // --- Docker Commands ---
      cmd("docker")
        .text("Docker Commands")
        .children(
          cmd("prune")
            .text("Docker remove unused data")
            .action((_, c) => c.copy(cmd = Some(DockerPrune()))),
          cmd("ps")
            .text("Docker lists containers")
            .action((_, c) => c.copy(cmd = Some(DockerPS()))),
          cmd("df")
            .text("Docker file system space usage")
            .action((_, c) => c.copy(cmd = Some(DockerDF()))),
          cmd("stats")
            .text("Docker containers usage statistics")
            .action((_, c) => c.copy(cmd = Some(DockerStats())))
        ),

      // --- AWS Commands ---
      cmd("aws")
        .text("AWS Actions")
        .children(
          cmd("start")
            .text("Start build on AWS CodeBuild")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildStart(x))))
            ),
          cmd("stop")
            .text("Stop build on AWS CodeBuild")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildStop(x))))
            ),
          cmd("info")
            .text("Show build info from AWS CodeBuild")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildInfo(x))))
            ),
          cmd("status")
            .text("Show build status from AWS CodeBuild")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildStatus(x))))
            ),
          cmd("logs")
            .text("Show logs for last build from AWS CodeBuild")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildLogs(x)))),
              opt[Unit]('f', "follow")
                .text("Follow log output")
                .action((_, c) =>
                  c.copy(cmd = c.cmd.collect { case it: CodeBuildLogs =>
                    it.copy(follow = true)
                  })
                )
            ),
          cmd("projects")
            .text("List all projects on AWS CodeBuild")
            .action((_, c) => c.copy(cmd = Some(CodeBuildProjects())))
        ),

      // --- API Commands ---
      cmd("api")
        .text("API Commands")
        .children(
          cmd("inc")
            .text("Increment service version")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildAppInc(x))))
            ),
          cmd("dec")
            .text("Decrement service version")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildAppDec(x))))
            ),
          cmd("version")
            .text("Show current service version")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildAppCurr(x))))
            ),
          cmd("info")
            .text("Show app info")
            .children(
              arg[String]("<App alias>")
                .action((x, c) => c.copy(cmd = Some(CodeBuildAppInfo(x))))
            ),
          cmd("apps")
            .text("Show app list")
            .action((_, c) => c.copy(cmd = Some(CodeBuildAppList()))),
          cmd("update")
            .text("API Update Commands")
            .children(
              cmd("alias")
                .text("Update app alias")
                .action((_, c) =>
                  c.copy(cmd = Some(CodeBuildUpdateAlias("", "")))
                )
                .children(
                  arg[String]("<Current alias>")
                    .action((x, c) =>
                      c.copy(cmd = c.cmd.collect {
                        case cb: CodeBuildUpdateAlias => cb.copy(fromAlias = x)
                      })
                    ),
                  arg[String]("<New alias>")
                    .action((x, c) =>
                      c.copy(cmd = c.cmd.collect {
                        case cb: CodeBuildUpdateAlias => cb.copy(toAlias = x)
                      })
                    )
                ),
              cmd("project")
                .text("Update code build project name")
                .action((_, c) =>
                  c.copy(cmd =
                    Some(CodeBuildUpdateCodeBuildProjectName("", ""))
                  )
                )
                .children(
                  arg[String]("<Current alias>")
                    .action((x, c) =>
                      c.copy(cmd = c.cmd.collect {
                        case cb: CodeBuildUpdateCodeBuildProjectName =>
                          cb.copy(alias = x)
                      })
                    ),
                  arg[String]("<New project name>")
                    .action((x, c) =>
                      c.copy(cmd = c.cmd.collect {
                        case cb: CodeBuildUpdateCodeBuildProjectName =>
                          cb.copy(newProjectName = x)
                      })
                    )
                )
            )
        )
    )

  def parse(args: Array[String]): Option[Cmd] =
    OParser.parse(parser, args, Config()).flatMap(_.cmd)
