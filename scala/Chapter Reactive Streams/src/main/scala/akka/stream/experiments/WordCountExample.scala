package akka.stream.experiments

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.NotUsed
import java.io.File
import akka.stream.scaladsl.Sink
import scala.concurrent.Future
import akka.Done
import scala.util.Success
import scala.util.Failure
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object WordCountExample extends App{
  
  implicit val actorSystem = ActorSystem("WordCount")
  implicit val materializer = ActorMaterializer()
  
  def buildSource(fileName: String): Source[String, NotUsed]  = {
    Source(List(scala.io.Source.fromFile(new File(fileName)).getLines().toList.mkString))
  }
  
  val source: Source[String, NotUsed] = buildSource("resources/WordCount1.txt")
  
  val res: Future[Done] = source.map { x => x.split(" ")
                                             .groupBy { x => x }
                                             .mapValues { x => x.length }
                                     }
                                .runWith(Sink.foreach { x => x.map(x => x match {
                                  case (word, count) => println(word +" = "+count)
                                }) })
                                                     
  res.onComplete { x => x match {
    
    case Success(x) => println("Success")
    case Failure(x) => println("Failure")
    }
    val terminate = actorSystem.terminate()
    val done = Await.result(terminate, Duration.Inf)
    Console.println("Done : "+done)
  }                              
  
}