package akka.stream.experiments

import akka.stream._
import akka.stream.scaladsl._
import akka.NotUsed
import akka.actor.ActorSystem
import akka.util.ByteString
import java.io.File

object Example1 extends App{
  
  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  
  val source: Source[Int, NotUsed] = Source(1 to 100)
  source.runForeach { println }(materializer)
  
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)
  
  val result = factorials.map {  num  => ByteString(num) }
                         .runWith(FileIO.toFile(new File("Output.txt")))
                         
  
  system.shutdown()
}