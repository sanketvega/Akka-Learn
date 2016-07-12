package crawler.exp

import akka.stream.actor.ActorPublisher
import java.net.URL
import akka.actor.ActorLogging
import crawler.exp.messages.Messages._

final class UrlProducer extends ActorPublisher[URL] with ActorLogging{
  
  def receive = {
    case Start =>
      log.info("Start Message Found")
      //TODO Fetch the list of URL
    case Stop =>
      log.info("Stop Message Found")
      context.stop(self)
    case StartURL(url) =>
      log.info("Initial URL : "+url.toString())
      //TODO Crawl
  }
  
}