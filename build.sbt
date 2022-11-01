ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "client_server"
  )

libraryDependencies += "org.postgresql" % "postgresql" % "42.2.16"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test