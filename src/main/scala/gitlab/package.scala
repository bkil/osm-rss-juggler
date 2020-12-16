import java.net.URL

import xml.{Html, Rss}

package object gitlab {
  def run(): Unit = {
    val url = new URL("https://gitlab.com/bkil/humor/-/commits/master?format=atom")
    val doc = GitlabParser.parse(Html.fetch(url))
    println(doc.items.length, doc.title)
    doc.save("gitlab-humor.xml")

    val prefix = "http://bkil.hu/kludge/redirect.html?url="
    val redirected = doc.transformItems(Rss.Item.prefixLink(prefix))
    redirected.save("gitlab-humor-redirect.xml")
  }
}
