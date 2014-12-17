import sbt._
import sbt.Keys._

object Build extends sbt.Build {

  def buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.benburton",
    scalaVersion := "2.10.0",
    resolvers ++= Seq(
      "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases",
      "typesafe releases" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.2.6",
      "org.specs2" %% "specs2" % "2.2.2" % "test"
    ),
    unmanagedBase <<= baseDirectory { base => base / "lib" },
    scalacOptions := Seq("-deprecation", "-encoding", "utf8")
  )

  val main = Project("scala-wistia", base = file("."), settings = buildSettings )
}