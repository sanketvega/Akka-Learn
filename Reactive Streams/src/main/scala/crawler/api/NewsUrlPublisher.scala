package crawler.api

import akka.stream.actor.ActorPublisher
import akka.actor.ActorLogging
import crawler.NewsResult

class NewsUrlPublisher extends ActorPublisher[NewsResult] with ActorLogging{
  
  def receive = {
    null
  }
  
}