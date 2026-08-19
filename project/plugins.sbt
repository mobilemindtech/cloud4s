resolvers += Resolver.mavenLocal

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.12")
addSbtPlugin("com.indoorvivants" % "bindgen-sbt-plugin" % "0.2.4")
addSbtPlugin("com.indoorvivants.vcpkg" % "sbt-vcpkg-native" % "0.0.21")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.4.0")
