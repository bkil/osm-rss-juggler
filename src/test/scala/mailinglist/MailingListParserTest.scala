package mailinglist

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class MailingListParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import MailingListParser.parse
    "1" in {
      val xml =
<rss version="2.0"><channel><title>openstreetmap-hungary</title>
<link>https://groups.google.com/d/forum/openstreetmap-hungary</link>
<description>Channel description</description>
<language>hu</language>
<item><title>Interesting subject</title>
<link>https://groups.google.com/d/msg/openstreetmap-hungary/42/69</link>
<description>Item &lt;i&gt;description&lt;/i&gt;text</description>
<guid isPermaLink="true">https://groups.google.com/d/topic/openstreetmap-hungary/42</guid>
<author>
OpenStreet Mapper</author>
<pubDate>Sat, 1 Apr 2020 13:37:00 UTC</pubDate></item>
</channel></rss>
      parse(xml) shouldBe Doc(
        title = "Mailing list (via bkil-bot)",
        items = List(
          Item(
            title = "Interesting subject: Item descriptiontext -OpenStreet Mapper",
            link = "https://groups.google.com/d/msg/openstreetmap-hungary/42/69",
            guid = "https://groups.google.com/d/topic/openstreetmap-hungary/42",
            pubDate = "Sat, 1 Apr 2020 13:37:00 UTC"
          )
        )
      )
    }
  }
}
