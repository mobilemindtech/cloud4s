package io.cloud4s.cli

import io.cloud4s.cli.data.AppVersion
import io.cloud4s.cli.data.AppInfo

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
