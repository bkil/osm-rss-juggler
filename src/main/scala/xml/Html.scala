package xml

object Html {
  def removeTags(str: String): String = {
    def stripper(s: String) = raw"(\s*(<[^>]*>\s*)|\s+)+".r.replaceAllIn(s, " ")

    stripper(str).trim
  }
}
