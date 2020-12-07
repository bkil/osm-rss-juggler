import java.net.URL

import xml.Html

package object gitlab {
  def run(): Unit = {
    val url = new URL("https://gitlab.com/bkil/humor/-/commits/master?format=atom")
    val doc = GitlabParser.parse(Html.fetch(url))
    println(doc.items.length, doc.title)
    doc.save("gitlab-humor.xml")
  }
}
