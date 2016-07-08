name := """Akka-Streams"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-stream_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-cluster_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-remote_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-persistence_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-contrib_2.11" % "2.4.7",
  "com.typesafe.akka" % "akka-stream-testkit_2.11" % "2.4.7",
  "io.spray" %% "spray-client" % "1.3.2",
  "org.jsoup" % "jsoup" % "1.9.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
