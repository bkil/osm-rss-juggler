package mastodon

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object Parser {

  def parse(xml: Elem, account: String): Doc = {
    val channel = xml \\ "channel"
    Doc(
      title = s"$account (via bkil-bot)",
      items = convertItems(channel \\ "item"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .map(n => Item(node = n, title = ItemProcessor.produceTitle(n))).toList
  }
}
