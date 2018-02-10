def json4sVersion = "3.6.0-M2"


lazy val akka_core = "com.typesafe.akka" %% "akka-actor" % "2.5.9"
lazy val json4sNative =  "org.json4s" %% "json4s-native" % json4sVersion

lazy val commonSettings = Seq(
  organization := "com.yih.paxos",
  version := "0.1.0",
  scalaVersion := "2.12.4",
  libraryDependencies ++= Seq(
    akka_core,
    json4sNative
  ),
  publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository")))
)

lazy val root = project in file(".") aggregate(paxos_unique, paxos_core, paxos_system) settings(
  commonSettings,
  name := "paxos"
)

lazy val paxos_unique = project in file("paxos-unique") settings (
  commonSettings,
  name := "paxos-unique"
)

lazy val paxos_core = project in file("paxos-core") dependsOn paxos_unique settings (
  commonSettings,
  name := "paxos-core"
  )

lazy val paxos_system = project in file("paxos-system") dependsOn paxos_core settings (
  commonSettings,
  name := "paxos-system"
  )
