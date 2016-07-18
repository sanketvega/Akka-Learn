name := """ReactiveAkka"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.7",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.7" % "test",
  "org.scalatest" %% "scalatest" % "2.2.6",
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.11",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.7",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.7",
  "com.typesafe.akka" %% "akka-stream" % "2.4.7",
  "com.typesafe.akka" %%"akka-stream-testkit" % "2.4.7",
  "org.scala-lang" % "scala-reflect" % "2.11.8",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
)
