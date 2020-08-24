package osmnotes

object NotesTextProcessor {
  def simplifyTitle(t: String): String = {
    noDuplicate(noJaras(noDistrict(ignoreDash(t))))
  }

  def ignoreDash(s: String) = s.replace("–", "-")

  def noDistrict(s: String) = raw"(, [1-9][0-9]*(st|nd|rd|th) district), .*".r.replaceAllIn(s, n => s"${n.group(1)})")

  def noJaras(s: String) = raw""", [^,]* (járás|Regional Unit)(, .*|)\)""".r.replaceAllIn(s, ")")

  def noDuplicate(s: String) =
    raw"""( \(near )([^,]*), ([^,]*)(, |\))""".r.replaceAllIn(s,
      m => if (m.group(2) == m.group(3))
        s"${m.group(1)}${m.group(2)}${m.group(4)}"
      else
        m.group(0)
    )

  def sliceCommentsInDescription(description: String): List[String] = {
    def chompPre(s: String) = raw"""(?s).*<h2>Full note</h2>\s*<div>\s*<div class="note-comment" style="margin-top: 5px">\s*<div class="note-comment-description" style="font-size: smaller; color: #999999">""".r.replaceAllIn(s, "")

    def chompSuf(s: String) = raw"""(?s)\s*</div>\s*</div>\s*</div>\s*""".r.replaceAllIn(s, "")

    chompSuf(chompPre(description))
      .split("""(?s)\s*</div>\s*</div>\s*<div class="note-comment" style="margin-top: 5px">\s*<div class="note-comment-description" style="font-size: smaller; color: #999999">""")
      .map(formatComment)
      .toList
  }

  def formatComment(s: String): String = {
    def emptyNull(str: String): String =
      if (str == null)
        ""
      else str
    val md =
      raw"""(?s)([^<>]*) <span title=" *([^" ]+) ([^" ]+) ([^" ]+) at ([0-9]{2}:[0-9]{2})">[^<>]*</span>( by( )<a href="https://www\.openstreetmap\.org/user/[^"]*">([^<>]*)</a>)?</div>\s*<div class="note-comment-text">"""
        .r.replaceAllIn(s, m => s"* ${m.group(4)} ${m.group(3)} ${m.group(2)} ${m.group(5)}${emptyNull(m.group(7))}${emptyNull(m.group(8))} ${m.group(1)}: ")
    raw"""(?s)(\s*\n\s*)+""".r.replaceAllIn(md, br)
  }

  val br = " / " // "<br />\n"
}
