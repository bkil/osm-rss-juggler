import java.net.URL

import xml.Html

package object mastodon {
  def run(): Unit = {
    val douUrl = new URL("https://en.osm.town/@Doudouosm.rss")
    val douDoc = MastodonParser.parse(Html.fetch(douUrl), "en.osm.town/@Doudouosm")
    println(douDoc.items.length, douDoc.title)
    douDoc.save("mastodon-doudouosm.xml")

    val osmUrl = new URL("https://en.osm.town/@openstreetmap.rss")
    val osmDoc = MastodonParser.parse(Html.fetch(osmUrl), "en.osm.town/@openstreetmap.rss")
    println(osmDoc.items.length, osmDoc.title)
    osmDoc.save("mastodon-openstreetmap.xml")
  }
}
