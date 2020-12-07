import java.net.URL

import xml.Html

package object mailinglist {
  def run(): Unit = {
    val url = new URL("https://groups.google.com/forum/feed/openstreetmap-hungary/msgs/rss.xml?num=50")
    val doc = MailingListParser.parse(Html.fetch(url))
    println(doc.items.length, doc.title)
    doc.save("mailing-list.xml")
  }
}
