package osmnotes

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class NotesTextProcessorTest extends AnyFreeSpec with Matchers {
  "simplifyTitle" - {
    import NotesTextProcessor.simplifyTitle
    "snip after járás" in {
      simplifyTitle(" (near Kométa 99 Zrt., Nagygát, Kaposvár, Kaposvári járás, Somogy, Southern Transdanubia, Transdanubia, 7400, Hungary)") shouldBe
        " (near Kométa 99 Zrt., Nagygát, Kaposvár)"
    }
    "snip after regional unit" in {
      simplifyTitle(" (near Isaszeg, Gödöllő Regional Unit, Pest megye, Central Hungary, 2117, Hungary)") shouldBe
        " (near Isaszeg)"
    }
    "snip after Budapest district" in {
      simplifyTitle(" (near Belső-Ferencváros, 9th district, Budapest, Central Hungary, Hungary)") shouldBe
        " (near Belső-Ferencváros, 9th district)"
    }
    "deduplicated locality" in {
      simplifyTitle(" (near Csepel–Gyártelep, Csepel-Gyártelep, 21st district, Budapest, Central Hungary, 1211, Hungary)") shouldBe
        " (near Csepel-Gyártelep, 21st district)"
    }
  }

  "addNoteIdToTitle" - {
    import NotesTextProcessor.addNoteIdToTitle
    "1" in {
      addNoteIdToTitle("x (y) (z)", "http://localhost/123#42") shouldBe "x 123 (y) (z)"
    }
  }

  "formatComment" - {
    import NotesTextProcessor.formatComment
    "1" in {
      val comment = """Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls see at note #2277015"""
      formatComment(comment) shouldBe "2020 July 24" -> "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015"
    }

    "2" in {
      val comment = """Created <span title="24 July 2020 at 10:41">19 days ago</span></div> <div class="note-comment-text">pls see at note #2277015"""
      formatComment(comment) shouldBe "2020 July 24" -> "* 2020 July 24 10:41 Created: pls see at note #2277015"
    }
  }

  "sliceCommentsInDescrption" - {
    import NotesTextProcessor.sliceCommentsInDescription
    "support compressed input" in {
      val desc =
        """
          |<h2>Comment</h2> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> <h2>Full note</h2> <div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="24 July 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls see at note #2277015</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029</div> </div> </div>
          |""".stripMargin
      sliceCommentsInDescription(desc) should contain theSameElementsAs List(
        "* 2020 July 24 10:41 HeidrichA Created: pls see at note #2277015",
        "* 2020 August 12 20:49 gabro00 Resolved: Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet. https://www.openstreetmap.org/changeset/89322029"
      )
    }

    "support wrapped input" in {
      val desc =
        """|<h2>Comment</h2>
           |<div class="note-comment" style="margin-top: 5px">
           |  <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">
           |about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div>
           |  <div class="note-comment-text">Több probléma is volt, igyekeztem mindet megoldani. Ha nem sikerült volna maradéktalanul, nyisd újra a jegyzetet.
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

    "ignore comments from the future" in {
      val desc =
        """
          |<h2>Comment</h2> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több</div> </div> <h2>Full note</h2> <div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="1 August 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="14 August 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">future</div> </div> </div>
          |""".stripMargin
      sliceCommentsInDescription(desc) should contain theSameElementsAs List(
        "* 2020 August 1 10:41 HeidrichA Created: pls",
        "* 2020 August 12 20:49 gabro00 Resolved: Több"
      )
    }

    "skip previous comments from same day" in {
      val desc =
        """
          |<h2>Comment</h2> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több</div> </div> <h2>Full note</h2> <div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Created <span title="12 August 2020 at 10:41">19 days ago</span> by <a href="https://www.openstreetmap.org/user/HeidrichA">HeidrichA</a></div> <div class="note-comment-text">pls</div> </div> <div class="note-comment" style="margin-top: 5px"> <div class="note-comment-description" style="font-size: smaller; color: #999999">Resolved <span title="12 August 2020 at 20:49">about 2 hours ago</span> by <a href="https://www.openstreetmap.org/user/gabro00">gabro00</a></div> <div class="note-comment-text">Több</div> </div> </div>
          |""".stripMargin
      sliceCommentsInDescription(desc) should contain theSameElementsAs List(
        "* 2020 August 12 20:49 gabro00 Resolved: Több"
      )
    }
  }

  "pruneComments" - {
    import NotesTextProcessor.pruneComments
    "preserve old history" in {
      pruneComments(List(
        "3" -> "c",
        "1" -> "a",
        "2" -> "b",
        "3" -> "c")) shouldBe
        List("a", "b", "c")
    }

    "preserve history in case of parse error" in {
      pruneComments(List(
        "33" -> "cc",
        "1" -> "a",
        "2" -> "b",
        "3" -> "c")) shouldBe
        List("a", "b", "c", "cc")
    }

    "skip future comment on old thread" in {
      pruneComments(List(
        "2" -> "b",
        "1" -> "a",
        "2" -> "b",
        "3" -> "c")) shouldBe
        List("a", "b")
    }

    "skip future comments on old thread" in {
      pruneComments(List(
        "1" -> "a",
        "1" -> "a",
        "2" -> "b",
        "3" -> "c")) shouldBe
        List("a")
    }

    "skip future comments on recent thread" in {
      pruneComments(List(
        "2" -> "b",
        "1" -> "a",
        "2" -> "b",
        "2" -> "c")) shouldBe
        List("a", "b")
    }

    "skip past comments on recent thread" in {
      pruneComments(List(
        "2" -> "c",
        "1" -> "a",
        "2" -> "b",
        "2" -> "c")) shouldBe
        List("c")
    }
  }
}
