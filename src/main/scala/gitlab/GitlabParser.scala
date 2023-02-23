package gitlab

import xml.Rss.{Doc, Item}

import scala.xml.{Elem, NodeSeq}

object GitlabParser {

  def parse(xml: Elem): Doc = {
    Doc(
      title = "git (via bkil-bot)",
      items = convertItems(xml \\ "entry"))
  }

  private def convertItems(seq: NodeSeq): List[Item] = {
    seq
      .filter(GitlabItemProcessor.itemFilter)
      .map(n =>
        Item(
          n,
          title = GitlabItemProcessor.produceTitle(n),
          link = Some(n \\ "link" \@ "href"),
          guid = Some((n \\ "id").text)
        )).toList
  }
}
