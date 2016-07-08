package crawler

trait NewsScraper {
  
  def scrape(url: String): NewsResult  
  
}