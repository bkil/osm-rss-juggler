package xml

import java.net.URL

import scala.xml.Source.fromInputStream
import scala.xml.{Elem, XML}

object Html {
  def removeTags(str: String): String = {
    def stripBreaking(s: String) =
      raw"</?(tr|td|table|br|hr|p)( [^>]*)?/?>".r.replaceAllIn(s, " ")
    def stripNonBreaking(s: String) =
      raw"<[^>]*>".r.replaceAllIn(s, "")
    def compressSpace(s: String) =
      raw"\s+".r.replaceAllIn(s, " ")

    compressSpace(stripNonBreaking(stripBreaking(str))).trim
  }

  def fetch(url: URL): Elem = {
    val con = url.openConnection()
    con.setRequestProperty("User-Agent", "osm-rss-juggler/0.1")
    XML.load(fromInputStream(con.getInputStream))
  }
}
