import java.net.URL

import scala.xml.XML

package object mailinglist {
  def run(): Unit = {
    val xml = XML.load(new URL("https://groups.google.com/forum/feed/openstreetmap-hungary/msgs/rss.xml?num=50"))
    val doc = MailingListParser.parse(xml)
    println(doc.items.length, doc.title)
    doc.save("mailing-list.xml")
  }
}
