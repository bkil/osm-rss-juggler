package xml

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
}
