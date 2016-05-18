package com.lesson1.basic.scala

import akka.actor.ActorSystem
import akka.testkit.TestActorRef

import scala.concurrent.duration._
import akka.util.Timeout

object Main extends App{
  
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)
  
  val actorRef = TestActorRef(new AkkaDB)
  actorRef ! SetRequest("key", "value")

  val akkaDb = actorRef.underlyingActor
  println(akkaDb.map.get("key")) 
  
  system.terminate()
}