package changesetdiscuss

import xml.Html

import scala.xml.Node

object ChangesetDiscussItemProcessor {
  def produceTitle(n: Node): String = {
    val r = raw"""^(?s)\s*<b>Contributor:</b>\s*<a [^>]*>([^<>]+)</a> </br>\s*<b>Discussion Comment No\.:</b> [0-9]+ </br>\s*<b>Comment:</b> (.*)""".r
    val content = (n \\ "content").text
    val html = content match {
      case r(name, text) =>
        val comment = raw""" *</br>\s*<b>Date:</b>[^<>]*$$""".r.replaceAllIn(text, "")
        s"$comment -$name"
      case _ =>
        content
    }
    Html.removeTags(html)
  }

  def produceLink(n: Node): String = {
    (n \\ "link").\@("href").replaceFirst("http:", "https:")
  }

  def produceGuid(n: Node): String = {
    val index = getCommentIndex(n).map(s => s"#comment_$s").getOrElse("")
    s"${produceLink(n)}$index"
  }

  def getCommentIndex(n: Node): Option[String] = {
    val r = raw"""^Changeset [0-9]+ \(Comment no\. ([0-9]+)\).*""".r
    (n \\ "title").text match {
      case r(i) => Some(i)
      case _ => None
    }
  }
}
