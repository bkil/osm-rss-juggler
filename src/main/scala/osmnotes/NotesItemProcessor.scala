package osmnotes

import osmnotes.NotesTextProcessor._

import scala.xml.Node

object NotesItemProcessor {
  def itemFilter(n: Node): Boolean = {
    (n \\ "title").text.endsWith(", Hungary)")
  }

  def produceTitle(n: Node): String = {
    val title = (n \\ "title").text
    val description = (n \\ "description").text
    val comments = sliceCommentsInDescription(description)
    val link = (n \\ "link").text

    (addNoteIdToTitle(simplifyTitle(title), link) :: comments).mkString(br)
  }
}
