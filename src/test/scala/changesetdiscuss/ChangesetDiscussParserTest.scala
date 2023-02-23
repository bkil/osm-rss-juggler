package changesetdiscuss

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class ChangesetDiscussParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import ChangesetDiscussParser.parse
    "1" in {
      val xml =
<feed xmlns="http://www.w3.org/2005/Atom" xmlns:thr="http://purl.org/syndication/thread/1.0" xml:lang="en">
  <author><name>Pascal Neis (Copyright - neis-one.org)</name></author>
  <title type="text">Latest OpenStreetMap OSM Changeset Discussions - Feed for Hungary</title>
  <link rel="alternate" type="text/html" href="https://resultmaps.neis-one.org/osm-discussions-feed?c=Hungary" />
  <subtitle type="text">powered by www.neis-one.org</subtitle>
  <updated>2020-12-31 12</updated>
  <entry>
    <id>http://www.openstreetmap.org/changeset/123456789</id>
    <title>Changeset 123456789 (Comment no. 2) by OpenStreet Mapper</title>
    <link href="http://www.openstreetmap.org/changeset/123456789" />
    <updated>2020-04-01T13:37:00+00:00</updated>
    <content type="html">
      &lt;b&gt;Contributor:&lt;/b&gt;  &lt;a target="_blank" href="http://www.openstreetmap.org/changeset/123456789"&gt;OpenStreet Mapper&lt;/a&gt; &lt;/br&gt;
      &lt;b&gt;Discussion Comment No.:&lt;/b&gt; 2 &lt;/br&gt;
      &lt;b&gt;Comment:&lt;/b&gt; Hi! &lt;/br&gt;Bye  &lt;/br&gt;
      &lt;b&gt;Date:&lt;/b&gt; 2020-04-01T13:37:00+00:00				</content>
  </entry>
</feed>

      parse(xml) shouldBe Doc(
        title = "Changeset discussion (via bkil-bot)",
        items = List(
          Item(
            title = "Hi! Bye -OpenStreet Mapper",
            link = "https://www.openstreetmap.org/changeset/123456789#comment_2",
            guid = "https://www.openstreetmap.org/changeset/123456789#comment_2",
            pubDate = "2020-04-01T13:37:00+00:00"
          )
        )
      )
    }
  }
}
