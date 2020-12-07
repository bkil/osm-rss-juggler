package gitlab

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class GitlabParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import GitlabParser.parse
    "1" in {
      val xml =
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/">
  <title>humor:master commits</title>
  <link href="https://gitlab.com/bkil/humor/-/commits/master?format=atom" rel="self" type="application/atom+xml"/>
  <link href="https://gitlab.com/bkil/humor/-/commits/master" rel="alternate" type="text/html"/>
  <id>https://gitlab.com/bkil/humor/-/commits/master</id>
  <updated>2020-04-01T13:37:00+00:00</updated>
  <entry>
    <id>https://gitlab.com/bkil/humor/-/commit/69</id>
    <link href="https://gitlab.com/bkil/humor/-/commit/69"/>
    <title>cc0: add license</title>
    <updated>2020-04-01T13:00:00+00:00</updated>
    <media:thumbnail width="40" height="40" url="https://secure.gravatar.com/avatar/1234?s=80&amp;d=identicon"/>
    <author>
      <name>Contributor</name>
      <email>contributor@example.org</email>
    </author>
    <summary type="html"></summary>
  </entry>
  <entry>
    <id>https://gitlab.com/bkil/humor/-/commit/666</id>
    <link href="https://gitlab.com/bkil/humor/-/commit/666"/>
    <title>Merge branch 'contrib' into 'master'</title>
    <updated>2020-04-01T13:37:00+00:00</updated>
    <media:thumbnail width="40" height="40" url="https://assets.gitlab-static.net/uploads/-/system/user/avatar/123456/avatar.png"/>
    <author>
      <name>Maintaner</name>
      <email>name@example.com</email>
    </author>
    <summary type="html">
      cc0/en-wordplay/anonymous: good one

      See merge request
    </summary>
  </entry>
  <entry>
    <id>https://gitlab.com/bkil/humor/-/commit/42</id>
    <link href="https://gitlab.com/bkil/humor/-/commit/42"/>
    <title>cc0/en-wordplay/anonymous: good one</title>
    <updated>2020-04-01T13:37:00+00:00</updated>
    <media:thumbnail width="40" height="40" url="https://secure.gravatar.com/avatar/1234?s=80&amp;d=identicon"/>
    <author>
      <name>Contributor</name>
      <email>contributor@example.org</email>
    </author>
    <summary type="html">
      Why did the chicken cross the road?
    </summary>
  </entry>
</feed>
      parse(xml) shouldBe Doc(
        title = "git (via bkil-bot)",
        items = List(
          Item(
            title = "Contributor: cc0: add license",
            link = "https://gitlab.com/bkil/humor/-/commit/69",
            guid = "https://gitlab.com/bkil/humor/-/commit/69",
            pubDate = "2020-04-01T13:00:00+00:00"
          ),
          Item(
            title = "Contributor: cc0/en-wordplay/anonymous: good one / Why did the chicken cross the road?",
            link = "https://gitlab.com/bkil/humor/-/commit/42",
            guid = "https://gitlab.com/bkil/humor/-/commit/42",
            pubDate = "2020-04-01T13:37:00+00:00"
          )
        )
      )
    }
  }
}
