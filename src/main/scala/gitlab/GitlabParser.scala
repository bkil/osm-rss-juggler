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
          title = GitlabItemProcessor.produceTitle(n),
          link = n \\ "link" \@ "href",
          guid = (n \\ "id").text,
          pubDate = (n \\ "updated").text
        )).toList
  }
}
