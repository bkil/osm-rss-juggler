package wiki

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xml.Rss.{Doc, Item}

class WikiParserTest extends AnyFreeSpec with Matchers {
  "parse" - {
    import WikiParser.parse
    "1" in {
      val xml =
<rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
  <channel>
    <title>OpenStreetMap Wiki  - Changes related to &quot;Hungary/Találkozók&quot; [en]</title>
    <link>https://wiki.openstreetmap.org/wiki/Special:RecentChangesLinked</link>
    <description>Related changes</description>
    <language>en</language>
    <generator>MediaWiki 1.33.4</generator>
    <lastBuildDate>Sat, 15 Aug 2020 15:24:25 GMT</lastBuildDate>
    <item version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
      <title>Hungary/Találkozók/2020-08-08-agard</title>
      <link>https://wiki.openstreetmap.org/w/index.php?title=Hungary/Tal%C3%A1lkoz%C3%B3k/2020-08-08-agard&amp;diff=2021552&amp;oldid=2019606</link>
      <guid isPermaLink="false">https://wiki.openstreetmap.org/w/index.php?title=Hungary/Tal%C3%A1lkoz%C3%B3k/2020-08-08-agard&amp;diff=2021552&amp;oldid=2019606</guid>
      <description>&lt;p&gt;&lt;span dir=&quot;auto&quot;&gt;&lt;span class=&quot;autocomment&quot;&gt;Eredmények: &lt;/span&gt; add&lt;/span&gt;&lt;/p&gt;
        &lt;table class=&quot;diff diff-contentalign-left&quot; data-mw=&quot;interface&quot;&gt;
        &lt;col class=&quot;diff-marker&quot; /&gt;
        &lt;col class=&quot;diff-content&quot; /&gt;
        &lt;col class=&quot;diff-marker&quot; /&gt;
        &lt;col class=&quot;diff-content&quot; /&gt;
        &lt;tr class=&quot;diff-title&quot; lang=&quot;en&quot;&gt;
        &lt;td colspan=&quot;2&quot; style=&quot;background-color: #fff; color: #222; text-align: center;&quot;&gt;← Older revision&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; style=&quot;background-color: #fff; color: #222; text-align: center;&quot;&gt;Revision as of 13:36, 13 August 2020&lt;/td&gt;
        &lt;/tr&gt;&lt;tr&gt;&lt;td colspan=&quot;4&quot; class=&quot;diff-multi&quot; lang=&quot;en&quot;&gt;(One intermediate revision by the same user not shown)&lt;/td&gt;&lt;/tr&gt;&lt;tr&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-lineno&quot;&gt;Line 148:&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-lineno&quot;&gt;Line 148:&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;td style=&quot;background-color: #f8f9fa; color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #eaecf0; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;/td&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;td style=&quot;background-color: #f8f9fa; color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #eaecf0; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;td style=&quot;background-color: #f8f9fa; color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #eaecf0; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;=== Eredmények ===&lt;/div&gt;&lt;/td&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;td style=&quot;background-color: #f8f9fa; color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #eaecf0; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;=== Eredmények ===&lt;/div&gt;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;−&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #ffe49c; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;+&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #a3d3ff; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;* [[User:bkil]]: [https://www.openstreetmap.org/changeset/89258409 89258409], [https://www.openstreetmap.org/changeset/89275234 89275234]&lt;/div&gt;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;−&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #ffe49c; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;{{Anniversary}}&lt;/div&gt;&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;−&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #ffe49c; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;−&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #ffe49c; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;[[Category:OpenStreetMap 2020 Anniversary]]&lt;/div&gt;&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;/tr&gt;
        &lt;tr&gt;
        &lt;td class=&quot;diff-marker&quot;&gt;−&lt;/td&gt;
        &lt;td style=&quot;color: #222; font-size: 88%; border-style: solid; border-width: 1px 1px 1px 4px; border-radius: 0.33em; border-color: #ffe49c; vertical-align: top; white-space: pre-wrap;&quot;&gt;&lt;div&gt;[[Category:16th Birthday|16th Birthday]]&lt;/div&gt;&lt;/td&gt;
        &lt;td colspan=&quot;2&quot; class=&quot;diff-empty&quot;&gt;&amp;#160;&lt;/td&gt;
        &lt;/tr&gt;

        &lt;!-- diff cache key wiki:diff:wikidiff2:1.12:old-2019606:rev-2021552:1.5.1:0 --&gt;
        &lt;/table&gt;</description>
      <pubDate>Thu, 13 Aug 2020 13:36:03 GMT</pubDate>
      <dc:creator>Bkil</dc:creator>
      <comments>https://wiki.openstreetmap.org/wiki/Talk:Hungary/Tal%C3%A1lkoz%C3%B3k/2020-08-08-agard</comments>
    </item>
</channel></rss>
      parse(xml) shouldBe Doc(
        title = "Wiki (via bkil-bot)",
        items = List(
          Item(
            title = "Hungary/Találkozók/2020-08-08-agard: Eredmények: add (Thu, 13 Aug 2020 13:36:03 GMT) -Bkil",
            link = "https://wiki.openstreetmap.org/w/index.php?title=Hungary/Tal%C3%A1lkoz%C3%B3k/2020-08-08-agard&diff=2021552&oldid=2019606",
            guid = "https://wiki.openstreetmap.org/w/index.php?title=Hungary/Tal%C3%A1lkoz%C3%B3k/2020-08-08-agard&diff=2021552",
            pubDate = "Thu, 13 Aug 2020 13:36:03 GMT"
          )
        )
      )
    }
  }
}
