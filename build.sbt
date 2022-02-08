import sbt.Keys._
import D._

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / organization := "net.gemelen.dev"

lazy val root = project
  .in(file("."))
  .settings(
    name := "pmt",
    libraryDependencies :=
      cats
      ++ circe
      ++ kantan
      ++ http4s
      ++ logging
      ++ tests
  )
