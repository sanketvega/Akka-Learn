package lesson1.homework

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by kkishore on 6/28/16.
  */
object LastStringApp extends App{

  val system = ActorSystem("Akka")
  implicit val timeOut = Timeout(1 seconds)
  val lastStringActor = system.actorOf(Props[StringActor])
  lastStringActor ! "C"
  lastStringActor ! "C++"
  lastStringActor ! "Pascal"
  lastStringActor ! "Java"
  lastStringActor ! "Scala"
  lastStringActor ! "Swift"
  lastStringActor ! "Rust"
  val result = lastStringActor ? GetLastValue
  //Await.result(result.mapTo[String], 10 second)
  import scala.concurrent.ExecutionContext.Implicits.global
  result.onComplete(x => {
    println(x.get)
  })
  system.terminate()
}
