
lazy val akka_core = "com.typesafe.akka" %% "akka-actor" % "2.5.9"


lazy val commonSettings = Seq(
  organization := "com.yih.paxos",
  version := "0.1.0",
  scalaVersion := "2.12.4",
  libraryDependencies ++= Seq(
    akka_core
  ),
  publishTo := Some(Resolver.file("file", new File(Path.userHome.absolutePath + "/.m2/repository")))
)

lazy val root = project in file(".") aggregate(paxos_core, paxos_system) settings(
  commonSettings,
  name := "paxos"
)

lazy val paxos_unique = project in file("paxos-unique") settings (
  commonSettings
)

lazy val paxos_core = project in file("paxos-core") dependsOn paxos_unique settings (
  commonSettings
  )

lazy val paxos_system = project in file("paxos-system") dependsOn paxos_core settings (
  commonSettings
  )
