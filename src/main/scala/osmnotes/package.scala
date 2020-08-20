import java.net.URL

import scala.xml.{Elem, XML}

package object osmnotes {
  def run(): Unit = {
    val xml = XML.load(new URL("https://www.openstreetmap.org/api/0.6/notes/feed?bbox=16.113886,45.737129,22.897709,48.585258"))
    val doc = Parser.parse(xml)
    println(doc.items.length, doc.title)
    doc.save("osm-notes.xml")
  }
}
