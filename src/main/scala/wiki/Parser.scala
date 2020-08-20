package wiki

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object Parser {

  def parse(xml: Elem): Doc = {
    val channel = xml \\ "channel"
    Doc(
      title = "Wiki (via bkil-bot)",
      items = convertItems(channel \\ "item"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .map(n => Item(node = n, title = ItemProcessor.produceTitle(n))).toList
  }
}
