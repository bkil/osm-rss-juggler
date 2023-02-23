package wigle

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object WigleParser {

  def parse(xml: Elem): Doc = {
    val channel = xml \\ "feed"
    val (title, items) = convertItems(channel \\ "entry")
    Doc(
      title = s"${title.getOrElse("Wigle.net forum")} (via bkil-bot)",
      items = items)
  }

  private def convertItems(seq: NodeSeq): (Option[String], List[Item]) = {
    seq.foldLeft((Option.empty[String], List.empty[Item])) {
      case ((title, elems), n) =>
        val link = Some((n \\ "id").text)
        (
          title match {
            case None => WigleItemProcessor.produceFeedTitle(n)
            case _ => title
          },
          Item(
            node = n,
            title = WigleItemProcessor.produceItemTitle(n),
            link = link,
            guid = link
          ) :: elems
        )
    } match {
      case (title, items) => (title, items.reverse)
    }
  }
}
