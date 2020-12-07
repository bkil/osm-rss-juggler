package gitlab

import osmnotes.NotesTextProcessor._
import xml.Html

import scala.xml.Node

object GitlabItemProcessor {
  def itemFilter(n: Node): Boolean = {
    raw"Merge branch '.*' into 'master'".r.findFirstIn((n \\ "title").text).isEmpty
  }

  def produceTitle(n: Node): String = {
    val author = (n \\ "author" \\ "name").text ++ ": "
    val title = (n \\ "title").text
    val description = (n \\ "summary").text
    if ((description != null) && description.nonEmpty)
      author ++ title ++ br ++ Html.removeTags(description)
    else
      author ++ title
  }
}
