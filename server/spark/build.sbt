
scalaVersion := "2.11.3"

name := "spark"
organization := "com.yih"
version := "1.0"
description := "spark learn."

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))