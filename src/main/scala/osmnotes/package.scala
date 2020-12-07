import java.net.URL

import xml.Html

package object osmnotes {
  def run(): Unit = {
    val url = new URL("https://www.openstreetmap.org/api/0.6/notes/feed?bbox=16.113886,45.737129,22.897709,48.585258")
    val doc = NotesParser.parse(Html.fetch(url))
    println(doc.items.length, doc.title)
    doc.save("osm-notes.xml")
  }
}
