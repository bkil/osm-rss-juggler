package mailinglist

import xml.Html

import scala.xml.Node

object MailingListItemProcessor {
  def produceTitle(n: Node): String = {
    val author = Html.removeTags((n \\ "author").text)
    val title = Html.removeTags((n \\ "title").text)
    val description = Html.removeTags((n \\ "description").text)
    s"$title: $description -$author"
  }

  def produceLink(n: Node): String = {
    val parse = raw"https://groups.google.com/d/msg/openstreetmap-hungary/([^/]+)/([^/]+)".r
    (n \\ "link").text match {
      case parse(subject, message) =>
        s"https://groups.google.com/g/openstreetmap-hungary/c/$subject/m/$message"
      case s => s
    }
  }
}
