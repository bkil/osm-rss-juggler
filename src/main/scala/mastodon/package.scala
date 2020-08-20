import java.net.URL

import scala.xml.XML

package object mastodon {
  def run(): Unit = {
    val douXml = XML.load(new URL("https://en.osm.town/@Doudouosm.rss"))
    val douDoc = MastodonParser.parse(douXml, "en.osm.town/@Doudouosm")
    println(douDoc.items.length, douDoc.title)
    douDoc.save("mastodon-doudouosm.xml")

    val osmXml = XML.load(new URL("https://en.osm.town/@openstreetmap.rss"))
    val osmDoc = MastodonParser.parse(osmXml, "en.osm.town/@openstreetmap.rss")
    println(osmDoc.items.length, osmDoc.title)
    osmDoc.save("mastodon-openstreetmap.xml")
  }
}
