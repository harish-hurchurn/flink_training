import sbt._
import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object Common {
  val dependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % Versions.scalaTest % "test",
    "org.apache.flink" %% "flink-scala" % Versions.flinkVersion,
    "org.apache.flink" %% "flink-streaming-scala" % Versions.flinkVersion
  )

  val settings: Seq[Def.Setting[_]] = Seq(

    artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
      artifact.name + "." + artifact.extension
    },

    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", _) => MergeStrategy.discard
      case _ => MergeStrategy.first
    },

    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),

    organization := "co.ioctl",
    parallelExecution in Test := false,

    run in Compile := Defaults.runTask(
      fullClasspath in Compile,
      mainClass in(Compile, run),
      runner in(Compile, run)
    ).evaluated,

    scalaVersion := Versions.scalaVersion,
    scalacOptions ++= Seq(
      "-deprecation"
    ),

    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
    test in assembly := {}
  )
}

object Versions {
  val flinkVersion = "1.4.1"
  val scalaTest = "3.0.1"
  val scalaVersion = "2.11.12"
}
