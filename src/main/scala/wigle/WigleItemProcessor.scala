package wigle

import xml.Html

import scala.xml.Node

object WigleItemProcessor {
  def produceItemTitle(n: Node): String = {
    val author = Html.removeTags((n \\ "author" \\ "name").text)
    val title = Html.removeTags((n \\ "title").text)
    val (_, subject) = splitTitle(title)
    val comment = Html.removeTags(produceComment(n))
    s"$subject: $comment -$author"
  }

  def produceComment(n: Node): String = {
    val content = (n \\ "content").text
    val suffix = raw"""^\s*""".r.replaceAllIn(content, "")
    val prefix = raw"""\s*$$""".r.replaceAllIn(suffix, "")
    raw"""\s*<p>Statistics: Posted by <a href="https://wigle\.net/phpbb/memberlist\.php[?]mode=viewprofile&amp;u=[0-9]+">[^<>]+</a> [—] [^<>]+</p><hr />\s*$$""".r.replaceAllIn(prefix, "").stripMargin
  }

  def produceFeedTitle(n: Node): Option[String] = {
    splitTitle(Html.removeTags((n \\ "title").text))._1
  }

  private def splitTitle(title: String): (Option[String], String) = {
    val i = title.indexOf("•")
    if (i < 0) {
      (None, title)
    } else {
      title.splitAt(i) match {
        case (a, b) => (Some(a.dropRight(1)), b.drop(2))
      }
    }
  }
}
