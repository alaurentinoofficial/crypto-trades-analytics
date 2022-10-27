// Plugins
enablePlugins(
  JavaAppPackaging,
  DockerPlugin
)

// Versioning
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.12.10"
ThisBuild / organization := "com.example"

// Dependencies
val flinkVersion = "1.15.2"
libraryDependencies ++= Seq(
  "org.apache.flink" % "flink-java-examples" % "0.10.2",
  "org.apache.flink" % "flink-connector-kafka" % flinkVersion,
  "org.apache.flink" % "flink-connector-base" % flinkVersion,
  "org.apache.flink" % "flink-clients" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % "provided",
  "org.apache.flink" %% "flink-scala" % flinkVersion % "provided",
  "com.google.code.gson" % "gson" % "2.10"
)

// Build
lazy val root = (project in file("."))
  .settings(name := "trades-aggregator")

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "_" + sv.binary + "_" + module.revision + "." + artifact.extension
}

// Assembly
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("org", "apache")     => MergeStrategy.last
  case PathList("io", "netty")     => MergeStrategy.last
  case _ => MergeStrategy.first
}
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)
assemblyJarName := s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"
