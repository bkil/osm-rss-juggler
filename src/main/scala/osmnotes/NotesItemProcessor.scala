package osmnotes

import osmnotes.NotesTextProcessor._

import scala.xml.Node

object NotesItemProcessor {
  def itemFilter(n: Node): Boolean = {
    titleFilter((n \\ "title").text)
  }

  def produceTitle(n: Node): String = {
    val title = (n \\ "title").text
    val description = (n \\ "description").text
    val comments = sliceCommentsInDescription(description)
    val link = (n \\ "link").text

    (addNoteIdToTitle(simplifyTitle(title), link) :: comments).mkString(br)
  }

  def titleFilter(title: String): Boolean = {
    title.matches(raw".*(\(near [0-9.]+, [0-9.]+|, Hungary)\)$$")
  }
}
