package crawler.exp.messages

import java.net.URL

object Messages {
  
  final case object Start
  final case object Stop
  final case class StartURL(url: URL)
  
}