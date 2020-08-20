package osmnotes

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class TextProcessorTest extends AnyFreeSpec with Matchers {
  "simplifyTitle" - {
    import osmnotes.TextProcessor.simplifyTitle
    "1" in {
      simplifyTitle(" (near Kométa 99 Zrt., Nagygát, Kaposvár, Kaposvári járás, Somogy, Southern Transdanubia, Transdanubia, 7400, Hungary)") shouldBe
        " (near Kométa 99 Zrt., Nagygát, Kaposvár)"
    }
    "2" in {
      simplifyTitle(" (near Belső-Ferencváros, 9th district, Budapest, Central Hungary, Hungary)") shouldBe
        " (near Belső-Ferencváros, 9th district)"
    }
    "3" in {
      simplifyTitle(" (near Csepel–Gyártelep, Csepel-Gyártelep, 21st district, Budapest, Central Hungary, 1211, Hungary)") shouldBe
        " (near Csepel-Gyártelep, 21st district)"
    }
  }

  "formatComment" - {
    import osmnotes.TextProcessor.formatComment
    "1" in {
      val comment = """Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls see at note #2277015"""
      formatComment(comment) shouldBe "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015"
    }

    "2" in {
      val comment = """Created <span title="24 July 2020 at 10:41">19 days ago</span></div> <div class="note-comment-text">pls see at note #2277015"""
      formatComment(comment) shouldBe "* 2020 July 24 10:41 Created: pls see at note #2277015"
    }
  }

  "sliceCommentsInDescrption" - {
    import osmnotes.TextProcessor.sliceCommentsInDescription
    "1" in {
      val desc =
        """
          |<h2>Comment</h2> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> <h2>Full note</h2> <div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls see at note #2277015</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> </div>
          |""".stripMargin
      sliceCommentsInDescription(desc) should contain theSameElementsAs List(
        "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015",
        "* 2020 August 12 20:49 gabro00 Resolved: Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029"
      )
    }

    "2" in {
      val desc =
        """|<h2>Comment</h2>
           |<div class="note-comment" style="margin-top: 5px">
           |  <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 Augu
           |st 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></
           |div>
           |  <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna marad
           |éktalanul, nyisd újra a jegyzetet.
           |
           |https://www.openstreetmap.org/changeset/89322029</div>
           |</div>
           |
           |<h2>Full note</h2>
           |<div>
           |  <div class="note-comment" style="margin-top: 5px">
           |  <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div>
           |  <div class="note-comment-text">pls see at note #2277015</div>
           |</div>
           |<div class="note-comment" style="margin-top: 5px">
           |  <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div>
           |  <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet.
           |
           |https://www.openstreetmap.org/changeset/89322029</div>
           |</div>
           |
           |</div>
           |
           |""".stripMargin
      sliceCommentsInDescription(desc) should contain theSameElementsAs List(
        "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015",
        "* 2020 August 12 20:49 gabro00 Resolved: Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. / https://www.openstreetmap.org/changeset/89322029"
      )
    }
  }
}
