import java.net.URL

import xml.Html

package object changesetdiscuss {
  def run(): Unit = {
    val url = new URL("https://resultmaps.neis-one.org/osm-discussions-feed?c=Hungary")
    val doc = ChangesetDiscussParser.parse(Html.fetch(url))
    println(doc.items.length, doc.title)
    doc.save("changeset-discuss.xml")
  }
}
