package changesetdiscuss

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object ChangesetDiscussParser {

  def parse(xml: Elem): Doc = {
    Doc(
      title = "Changeset discussion (via bkil-bot)",
      items = convertItems(xml \\ "entry"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .map(n => {
        Item(
          node = n,
          title = ChangesetDiscussItemProcessor.produceTitle(n),
          link = Some(ChangesetDiscussItemProcessor.produceLink(n)),
          guid = Some(ChangesetDiscussItemProcessor.produceGuid(n))
        )
      }).toList
  }
}
