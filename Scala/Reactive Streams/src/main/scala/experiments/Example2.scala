package experiments

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.NotUsed
import akka.stream.scaladsl.Sink
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Example2 extends App {

  implicit val system = ActorSystem("Twitter-Example")
  implicit val materializer = ActorMaterializer()

  final case class Author(handler: String)

  final case class HashTag(name: String)

  final case class Tweet(author: Author, timeStamp: Long, body: String) {

    def hashTags(): Set[HashTag] = {
      body.split(" ").collect { case t: String if t startsWith ("#") => HashTag(t) }.toSet
    }
  }
  
  val akka = HashTag("#akka")
  
  val tweets: Source[Tweet, NotUsed] = Source(
    Tweet(Author("rolandkuhn"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("patriknw"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("bantonsson"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("drewhk"), System.currentTimeMillis, "#akka !") ::
      Tweet(Author("ktosopl"), System.currentTimeMillis, "#akka on the rocks!") ::
      Tweet(Author("mmartynas"), System.currentTimeMillis, "wow #akka !") ::
      Tweet(Author("akkateam"), System.currentTimeMillis, "#akka rocks!") ::
      Tweet(Author("bananaman"), System.currentTimeMillis, "#bananas rock!") ::
      Tweet(Author("appleman"), System.currentTimeMillis, "#apples rock!") ::
      Tweet(Author("drama"), System.currentTimeMillis, "we compared #apples to #oranges!") ::
      Nil)
      
   val authors: Source[Author, NotUsed] = tweets.filter { x => x.hashTags().contains(akka) }.map { x => x.author }
   
   authors.runWith(Sink.foreach { println })
   
   import system.dispatcher
   val done = system.terminate()
   
   done.onComplete { x => x match {
       case Success(x) => println("Success")
       case Failure(x) => println("Failure")
     }
     val res = Await.result(done, Duration.Inf)
     Console.println("Done : "+res)
   }

}

