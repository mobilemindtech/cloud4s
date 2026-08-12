resolvers += Resolver.mavenLocal

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "2.4.1")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")
addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.5.12")
//addSbtPlugin("br.com.mobilemind" % "sg4s" % "0.1.0-SNAPSHOT")
addSbtPlugin("com.indoorvivants" % "bindgen-sbt-plugin" % "0.2.4")
addSbtPlugin("com.indoorvivants.vcpkg" % "sbt-vcpkg-native" % "0.0.21")
