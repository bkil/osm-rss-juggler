package wiki

import xml.Html

import scala.xml.Node

object ItemProcessor {
  def produceTitle(n: Node): String = {
    val dcCreator = (n \\ "creator").text
    val title = Html.removeTags((n \\ "title").text)
    val pubDate = (n \\ "pubDate").text
    val fullDescription = (n \\ "description").text
    val description = Html.removeTags(fullDescription.split("\n").head)
    println(n)
    s"$title: $description ($pubDate) -$dcCreator"
  }
}
