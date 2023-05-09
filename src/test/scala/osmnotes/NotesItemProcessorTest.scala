package osmnotes

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class NotesItemProcessorTest extends AnyFreeSpec with Matchers {
  "produceTitle" - {
    import NotesItemProcessor.produceTitle
    "1" in {
      val xml =
        <item>
          <title>closed note (near Szeged, Szeged, Szegedi járás)</title>
          <link>https://www.openstreetmap.org/note/1234567#c654321</link>
          <description>
            <![CDATA[
<h2>Comment</h2> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> <h2>Full note</h2> <div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls see at note #2277015</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> </div>
]]>
          </description>
        </item>
      produceTitle(xml) shouldBe List(
        "closed note 1234567 (near Szeged)",
        "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015",
        "* 2020 August 12 20:49 gabro00 Resolved: Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029").mkString(NotesTextProcessor.br)
    }
  }
}
