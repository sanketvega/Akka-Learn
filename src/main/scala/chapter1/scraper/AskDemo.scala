package chapter1.scraper

import akka.util.Timeout
import akka.actor.Actor
import akka.pattern.ask
import com.akkademy.messages.GetRequest
import scala.concurrent.Future
import com.akkademy.messages.SetRequest

class AskDemo(cacheActorPath: String, httpActorPath: String, articleParserActorPath: String, 
    implicit val timeout: Timeout) extends Actor{
  
  val cacheActor = context.actorSelection(cacheActorPath)
  val httpActor = context.actorSelection(httpActorPath)
  val articleParserActor = context.actorSelection(articleParserActorPath)
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def receive = {
    case ParseArticle(url) => {
      val senderRef = sender()
      
      val cacheResult = cacheActor ? GetRequest(url)
      
      val result = cacheResult.recoverWith{
        case _:Exception => 
          val htmlContent = httpActor ? url
          
          htmlContent flatMap { 
            case HttpResponse(body) => articleParserActor ? ParseHtmlString(url, body)
            case x => Future.failed(new Exception("Unknown Response"))
          }
        
      }
      
      result.onComplete { 
        case scala.util.Success(x: String) => {
          println("cached result")
          senderRef ! x
        }
        case scala.util.Success(article: ArticleBody) => {
          cacheActor ! SetRequest(url, article.article)
          senderRef ! article
        }
        case scala.util.Failure(x) => akka.actor.Status.Failure(x)
        case x => println("Unknown Message")
      }
    }
  }
  
}