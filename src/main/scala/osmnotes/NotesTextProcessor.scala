package osmnotes

object NotesTextProcessor {
  def simplifyTitle(t: String): String = {
    noDuplicate(noRegion(noJaras(noDistrict(ignoreDash(t)))))
  }

  def ignoreDash(s: String) = s.replace("–", "-")

  def noDistrict(s: String) = raw"(, [1-9][0-9]*(st|nd|rd|th) district), .*".r.replaceAllIn(s, n => s"${n.group(1)})")

  def noJaras(s: String) = raw""", [^,]* (járás|Regional Unit)(, .*|)\)""".r.replaceAllIn(s, ")")

  def noRegion(s: String) = raw""", (Transdanubia|Great Plain and North),""".r.replaceAllIn(s, ",")

  def noDuplicate(s: String) =
    raw"""( \(near )([^,]*), ([^,]*)(, |\))""".r.replaceAllIn(s,
      m => if (m.group(2) == m.group(3))
        s"${m.group(1)}${m.group(2)}${m.group(4)}"
      else
        m.group(0)
    )

  def sliceCommentsInDescription(description: String): List[String] = {
    def chompPre(s: String) = raw"""<h2>[^<>]*</h2>\s*(<div>)?""".r.replaceAllIn(s, "")

    def chompSuf(s: String) = raw"""(?s)\s*</div>\s*</div>\s*</div>\s*""".r.replaceAllIn(s, "")

    val allComments = chompSuf(chompPre(description))
      .split("""(?s)(\s*</div>\s*</div>)?\s*<div class="note-comment" style="margin-top: 5px">\s*<div class="note-comment-description" style="font-size: smaller; color: #999999">""")
      .filter(_.nonEmpty)
      .map(formatComment)
      .toList
    pruneComments(allComments)
  }

  def pruneComments(allComments: List[(String, String)]): List[String] = {
    allComments match {
      case recent :: rest =>
        val (times, comments) = (rest.takeWhile(_ != recent) :+ recent).unzip
        if (times.takeRight(2).toSet.size == 2)
          comments
        else
          comments.takeRight(1)
      case _ =>
        allComments.map(_._2)
    }
  }

  def formatComment(s: String): (String, String) = {
    def emptyNull(str: String): String =
      if (str == null)
        ""
      else str

    def dropWraps(str: String): String =
      raw"""(?s)(\s*\n\s*)+""".r.replaceAllIn(str, br)

    val formatter = raw"""(?s)([^<>]*) <span title=" *([^" ]+) ([^" ]+) ([^" ]+) at ([0-9]{2}:[0-9]{2})">[^<>]*</span>( by( )<a href="https://www\.openstreetmap\.org/user/[^"]*">([^<>]*)</a>)?</div>\s*<div class="note-comment-text">(.*)""".r
    s match {
      case formatter(action, day, month, year, time, _, creatorSpace, creatorName, comment) =>
        s"$year $month $day" ->
          s"* $year $month $day $time${emptyNull(creatorSpace)}${emptyNull(creatorName)} $action: ${dropWraps(comment)}"
      case _ => s -> s
    }
  }

  val br = " / " // "<br />\n"
}
