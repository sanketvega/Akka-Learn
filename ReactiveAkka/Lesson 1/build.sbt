name := """Reactive-Akka-Lesson 1"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.4",
  "com.typesafe.akka" % "akka-http-core-experimental_2.11" % "2.0.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test"
)
libraryDependencies += "com.typesafe.akka" % "akka-stream_2.11" % "2.4.4"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.4"
