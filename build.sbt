import sbt.Keys.name

name := "flink_training"

lazy val flink_training = (project in file("."))
  .aggregate(word_count)
  .settings(Common.settings: _*)

lazy val word_count = (project in file("word_count"))
  .configs(IntegrationTest)
  .settings(Common.settings: _*)
  .settings(
    Defaults.itSettings,
    libraryDependencies ++= Common.dependencies,
    name := "word_count",
    version := "0.1.0-SNAPSHOT"
  )

wartremoverErrors ++= Warts.all