package wigle

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class WigleParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import WigleParser.parse
    "1" in {
      val xml =
<feed xmlns="http://www.w3.org/2005/Atom" xml:lang="en-gb">
  <link rel="self" type="application/atom+xml" href="//wigle.net/phpbb/app.php/feed/forum/6" />

  <title>WiGLE.net</title>
  <subtitle>Wireless Geographic Logging Engine</subtitle>
  <link href="https://wigle.net/phpbb/index.php" />
  <updated>2021-04-01T13:37:00</updated>

  <author><name><![CDATA[WiGLE.net]]></name></author>
  <id>//wigle.net/phpbb/app.php/feed/forum/6</id>

  <entry>
    <author><name><![CDATA[moderator]]></name></author>
    <updated>2021-04-02T13:37:42</updated>

    <published>2021-04-02T13:37:00</published>
    <id>https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56789#p56789</id>
    <link href="https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56789#p56789"/>
    <title type="html"><![CDATA[WiGLE Frontpage • Re: 20 years ago...]]></title>

    <category term="WiGLE Frontpage" scheme="https://wigle.net/phpbb/viewforum.php?f=6" label="WiGLE Frontpage"/>

    <content type="html" xml:base="https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56789#p56789"><![CDATA[
Thanks.<p>Statistics: Posted by <a href="https://wigle.net/phpbb/memberlist.php?mode=viewprofile&amp;u=42">moderator</a> — Fri Apr 02, 2021 1:37 pm</p><hr />
]]></content>
  </entry>
  <entry>
    <author><name><![CDATA[admin]]></name></author>
    <updated>2021-03-02T13:37:42</updated>

    <published>2021-03-02T13:37:00</published>
    <id>https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56788#p56788</id>
    <link href="https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56788#p56788"/>
    <title type="html"><![CDATA[WiGLE Frontpage • Subject]]></title>

    <category term="WiGLE Frontpage" scheme="https://wigle.net/phpbb/viewforum.php?f=6" label="WiGLE Frontpage"/>

    <content type="html" xml:base="https://wigle.net/phpbb/viewtopic.php?t=1234&amp;p=56789#p56788"><![CDATA[
OK
]]></content>
  </entry>
</feed>
      parse(xml) shouldBe Doc(
        title = "WiGLE Frontpage (via bkil-bot)",
        items = List(
          Item(
            title = "Re: 20 years ago...: Thanks. -moderator",
            link = "https://wigle.net/phpbb/viewtopic.php?t=1234&p=56789#p56789",
            guid = "https://wigle.net/phpbb/viewtopic.php?t=1234&p=56789#p56789",
            pubDate = "2021-04-02T13:37:00"
          ),
          Item(
            title = "Subject: OK -admin",
            link = "https://wigle.net/phpbb/viewtopic.php?t=1234&p=56788#p56788",
            guid = "https://wigle.net/phpbb/viewtopic.php?t=1234&p=56788#p56788",
            pubDate = "2021-03-02T13:37:00"
          )
        )
      )
    }
  }
}
