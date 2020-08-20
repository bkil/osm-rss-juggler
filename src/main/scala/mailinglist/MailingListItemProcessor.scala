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
}
