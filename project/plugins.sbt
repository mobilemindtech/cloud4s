resolvers += Resolver.mavenLocal

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.12")
addSbtPlugin("com.indoorvivants" % "bindgen-sbt-plugin" % "0.4.5")
addSbtPlugin("com.indoorvivants.vcpkg" % "sbt-vcpkg-native" % "0.0.21")
