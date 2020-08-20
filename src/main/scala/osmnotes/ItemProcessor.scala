package osmnotes

import osmnotes.TextProcessor._

import scala.xml.Node

object ItemProcessor {
  def itemFilter(n: Node): Boolean = {
    (n \\ "title").text.endsWith(", Hungary)")
  }

  def produceTitle(n: Node): String = {
    val title = (n \\ "title").text
    val description = (n \\ "description").text
    val comments = sliceCommentsInDescription(description)

    (simplifyTitle(title) :: comments).mkString(br)
  }
}
