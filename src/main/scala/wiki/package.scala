import java.net.URL

import xml.Html

package object wiki {
  def run(): Unit = {
    val xml1 = new URL("https://wiki.openstreetmap.org/w/api.php?hidebots=1&hideWikibase=1&urlversion=2&days=1&limit=5&target=Hungary/Tal%C3%A1lkoz%C3%B3k&action=feedrecentchanges&feedformat=rss")
    val doc1 = WikiParser.parse(Html.fetch(xml1))

    val xml2 = new URL("https://wiki.openstreetmap.org/w/api.php?hidebots=1&hideWikibase=1&urlversion=2&days=1&limit=5&target=Hungary&action=feedrecentchanges&feedformat=rss")
    val doc2 = WikiParser.parse(Html.fetch(xml2))

    val doc = doc1.distinctConcat(doc2)

    println(doc.items.length, doc.title)
    doc.save("wiki.xml")
  }
}
