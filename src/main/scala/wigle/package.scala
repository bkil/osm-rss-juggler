import java.net.URL

import xml.Html

package object wigle {
  def run(): Unit = {
    List(2, 3, 4, 6).foreach { f =>
      val url = new URL(s"https://www.wigle.net/phpbb/app.php/feed/forum/$f")
      WigleParser.parse(Html.fetch(url)).save(s"wigle-forum-$f.xml")
    }
  }
}
