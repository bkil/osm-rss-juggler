package xml

import java.io.PrintWriter

import scala.xml.{Elem, Node, XML}

object Rss {

  final case class Doc(title: String, items: List[Item]) {
    def distinctConcat(doc: Doc): Doc = {
      Doc(title = title, items = (items ++ doc.items).distinct)
    }

    def transformItems(f: Item => Item): Doc = {
      this.copy(
        items = items.map(f)
      )
    }

    def save(file: String): Unit = {
      XML.save(filename = file, node = toXml, xmlDecl = true)
    }

    def print(): Unit = {
      val writer = new PrintWriter(Console.out)
      XML.write(w = writer, node = toXml, enc = "UTF-8", xmlDecl = true, doctype = null)
      writer.flush()
      writer.close()
    }

    def toXml: Elem = {
      def makeItem(item: Item): Elem = {
      <item>
        <title>{item.title}</title>
        <link>{item.link}</link>
        <guid>{item.guid}</guid>
        <pubDate>{item.pubDate}</pubDate>
      </item>

      }

<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#" xmlns:georss="http://www.georss.org/georss">
  <channel>
    <title>{title}</title>
      {items.map(makeItem)}
  </channel>
</rss>
    }
  }

  final case class Item(title: String, link: String, guid: String, pubDate: String)

  object Item {
    def apply(node: Node, title: String, guid: Option[String] = None): Item =
      Item(
        guid = guid.getOrElse((node \\ "guid").text),
        title = title,
        link = (node \\ "link").text,
        pubDate = (node \\ "pubDate").text
      )

    def prefixLink(prefix: String): Item => Item = {
      i => i.copy(link = s"$prefix${i.link}")
    }
  }
}
