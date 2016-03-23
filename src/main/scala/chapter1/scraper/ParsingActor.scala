package chapter1.scraper

import akka.actor.Actor
import de.l3s.boilerpipe.extractors.ArticleExtractor

class ParsingActor extends Actor{
  
  override def receive = {
    case ParseHtmlString(url: String, htmlBody: String) => sender() ! ArticleExtractor.getInstance.getText(htmlBody)
    case x => println("Unknown Message : "+x.getClass())
  }
  
}