package chapter1.scraper

final case class ParseArticle(url: String)
final case class ParseHtmlString(url: String, htmlContent: String)
final case class HttpResponse(body: String)
final case class ArticleBody(url: String, article: String)