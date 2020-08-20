package osmnotes

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object Parser {

  def parse(xml: Elem): Doc = {
    val channel = xml \\ "channel"
    Doc(
      title = "Notes (via bkil-bot)",
      items = convertItems(channel \\ "item"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .filter(ItemProcessor.itemFilter)
      .map(n => Item(
      guid = (n \\ "guid").text,
      title = ItemProcessor.produceTitle(n),
      link = (n \\ "link").text
    )).toList
  }
}
