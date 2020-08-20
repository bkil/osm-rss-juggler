package mastodon

import xml.Html

import scala.xml.Node

object ItemProcessor {
  def produceTitle(n: Node): String = {
    val description = Html.removeTags((n \\ "description").text)
    if (description.nonEmpty)
      description
    else
      Html.removeTags((n \\ "title").text)
  }
}
