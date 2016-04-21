package akka.stream.experiments

import akka.stream._
import akka.stream.scaladsl._
import akka.NotUsed
import akka.actor.ActorSystem
import akka.util.ByteString
import java.io.File
import scala.concurrent.Future
import akka.Done
import scala.concurrent.duration.FiniteDuration._
import java.util.concurrent.TimeUnit
import java.io.PrintStream
import scala.util.{Success, Failure}
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, FiniteDuration}


object Example1 extends App{  
  
  //System.setOut(new PrintStream("Result.txt"))
  
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  
  val source: Source[Int, NotUsed] = Source(1 to 100)
  source.runForeach { println }(materializer)
  
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)
  
  val result: Future[IOResult] = factorials.map {  num  => ByteString(num+"\n") }
                         .runWith(FileIO.toFile(new File("Output.txt")))                                     
                         
  
  // A Reusable lineSink Method                         
  def lineSink(fileName: String): Sink[String, Future[IOResult]] = {
    Flow[String].map { num => ByteString(s"$num\n") }
                .toMat(FileIO.toFile(new File(fileName)))(Keep.right)
  }
  factorials.map { _.toString()}.runWith(lineSink("Factorial.txt"))
  
  //Time Based Stream Processing
  val done: Future[Done] = factorials.zipWith(Source(0 to 100))((acc, next) => s"$acc != $next")
                                     .throttle(10, FiniteDuration.apply(5, TimeUnit.SECONDS), 20, ThrottleMode.shaping)
                                     .runForeach { println }
  import system.dispatcher 
  done.onComplete { x => x match {
      case Success(x) => println("Success")
      case Failure(x) => println("Failure")
    }
    val terminate = system.terminate();
    val res = Await.result(terminate, Duration.Inf)
  }
                             
  //system.shutdown()                       
}