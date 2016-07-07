package com.akka.stream.experiments

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.stream.scaladsl.Flow
import akka.stream.scaladsl.Sink
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.stream.experiments.processors.BasicProcessors._


object BasicExamples {

  

  def main(ags: Array[String]) {

    implicit val system = ActorSystem("Basic-Example")
    implicit val materializer = ActorMaterializer()

    val source: Source[Long, NotUsed] = Source(1L to 10)

    val done = source.via(processA()).async
      .via(processB()).async
      .via(processC()).async
      .runWith(Sink.foreach { println })

    import system.dispatcher
    done.onComplete { x =>
      x match {
        case Success(x) => println("Success")
        case Failure(x) => println("Failure")
      }
      val terminate = system.terminate();
      val res = Await.result(terminate, Duration.Inf)
    }

  }
}

