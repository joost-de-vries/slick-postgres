name := "slick-postgres"

scalaVersion := "2.11.6"
scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation",
  "-Xlint",  "-encoding", "UTF8",
//  "-Ybackend:GenBCode","-Ydelambdafy:method",
  "-target:jvm-1.8",
  "-Ywarn-unused-import")
incOptions := incOptions.value.withNameHashing(true)
updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)

initialize := {
  val required = "1.8"
  val current = sys.props("java.specification.version")
  assert(current == required, s"Unsupported JDK: java.specification.version $current != $required")
}

mainClass in Compile := Some("HelloSlick")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc42",
  // extensions for postgres
  "com.github.tminglei" % "slick-pg_core_2.11" % "0.10.1",
  "com.github.tminglei" % "slick-pg_2.11" % "0.10.1",
  "com.typesafe.play" % "play-json_2.11" % "2.4.4",
  "com.github.tminglei" %% "slick-pg_date2" % "0.10.1",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.10.1",

  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.h2database" % "h2" % "1.4.187",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

fork in run := true
