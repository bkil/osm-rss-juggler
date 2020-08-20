package mastodon

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class ParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import Parser.parse
    "1" in {
      val xml =
<rss version="2.0" xmlns:webfeeds="http://webfeeds.org/rss/1.0">
  <channel>
    <title>DoudouOSM (@Doudouosm@en.osm.town)</title>
    <description>1.68K Toots, 222 Following, 182 Followers Â· #OpenStreetMap #OSM contributor 🗺🇧🇪
Also posting about open data, open source, free software, #fediverse, #IoT, #privacy, #quantum computing</description>
    <link>https://en.osm.town/@Doudouosm</link>
    <webfeeds:logo>https://en.osm.town/packs/media/images/logo-33a0fb4c065a0ccb90b51fcfdea6b3cf.svg</webfeeds:logo>
    <webfeeds:accentColor>2b90d9</webfeeds:accentColor>
    <image>
      <url>https://cf.mastohost.com/v1/AUTH_91eb37814936490c95da7b85993cc2ff/enosmtown/accounts/avatars/000/007/
495/original/fcda8323354f6dd3.png</url>
      <title></title>
      <link></link>
    </image>
    <webfeeds:icon>https://cf.mastohost.com/v1/AUTH_91eb37814936490c95da7b85993cc2ff/enosmtown/accounts/avatars/
000/007/495/original/fcda8323354f6dd3.png</webfeeds:icon>
    <webfeeds:cover image="https://cf.mastohost.com/v1/AUTH_91eb37814936490c95da7b85993cc2ff/enosmtown/accounts/
headers/000/007/495/original/34900e788389bb18.jpeg"/>
    <item>
      <title>Doudouosm: ““Open Advice” is a knowledge c…”</title>
      <guid isPermalink="true">https://en.osm.town/@Doudouosm/104690824925256017</guid>
      <link>https://en.osm.town/@Doudouosm/104690824925256017</link>
      <pubDate>Sat, 15 Aug 2020 01:33:34 +0000</pubDate>
      <description>&lt;p&gt;“Open Advice” is a knowledge collection from a wide variety of Free Software projects. It answers the question what 42 prominent contributors would have liked to know when they started, so you can get a head-start no matter how and where you contribute.&lt;/p&gt;&lt;p&gt;Read it: &lt;a href="http://www.open-advice.org" rel="nofollow noopener noreferrer" target="_blank"&gt;&lt;span class="invisible"&gt;http://www.&lt;/span&gt;&lt;span class=""&gt;open-advice.org&lt;/span&gt;&lt;span class="invisible"&gt;&lt;/span&gt;&lt;/a&gt;.&lt;/p&gt;&lt;p&gt;It’s 310-pages long read, released under &lt;a href="https://en.osm.town/tags/CC" class="mention hashtag" rel="tag"&gt;#&lt;span&gt;CC&lt;/span&gt;&lt;/a&gt;-BY-SA, but organized in small chapters, so you can cherry-pick.&lt;/p&gt;&lt;p&gt;&lt;a href="https://en.osm.town/tags/ebooks" class="mention hashtag" rel="tag"&gt;#&lt;span&gt;ebooks&lt;/span&gt;&lt;/a&gt; &lt;a href="https://en.osm.town/tags/FOSS" class="mention hashtag" rel="tag"&gt;#&lt;span&gt;FOSS&lt;/span&gt;&lt;/a&gt; &lt;a href="https://en.osm.town/tags/OpenAdvice" class="mention hashtag" rel="tag"&gt;#&lt;span&gt;OpenAdvice&lt;/span&gt;&lt;/a&gt;&lt;/p&gt;</description>
    </item>
</channel>
</rss>
      parse(xml, "dudu") shouldBe Doc(
        title = "dudu (via bkil-bot)",
        items = List(
          Item(
            title = "“Open Advice” is a knowledge collection from a wide variety of Free Software projects." +
              " It answers the question what 42 prominent contributors would have liked to know when they started," +
              " so you can get a head-start no matter how and where you contribute. Read it: http://www. open-advice.org ." +
              " It’s 310-pages long read, released under # CC -BY-SA, but organized in small chapters," +
              " so you can cherry-pick. # ebooks # FOSS # OpenAdvice",
            link = "https://en.osm.town/@Doudouosm/104690824925256017",
            guid = "https://en.osm.town/@Doudouosm/104690824925256017"
          )
        )
      )
    }
  }
}
