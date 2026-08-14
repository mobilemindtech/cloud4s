package io.cloud4s.cli

import upickle.implicits.key
import upickle.default.{macroRW, ReadWriter as RW}

object data:
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
