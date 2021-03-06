package wiki

import xml.Html

import scala.xml.Node

object WikiItemProcessor {
  def produceTitle(n: Node): String = {
    val dcCreator = (n \\ "creator").text
    val title = Html.removeTags((n \\ "title").text)
    val fullDescription = (n \\ "description").text
    val description = Html.removeTags(fullDescription.split("\n").head)
    s"$title: $description -$dcCreator"
  }
}
