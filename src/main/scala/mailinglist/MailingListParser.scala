package mailinglist

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object MailingListParser {

  def parse(xml: Elem): Doc = {
    val channel = xml \\ "channel"
    Doc(
      title = "Mailing list (via bkil-bot)",
      items = convertItems(channel \\ "item"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .map(n => {
        val link = MailingListItemProcessor.produceLink(n)
        Item(node = n, title = MailingListItemProcessor.produceTitle(n), link = Some(link), guid = Some(link))
      }).toList
  }
}
