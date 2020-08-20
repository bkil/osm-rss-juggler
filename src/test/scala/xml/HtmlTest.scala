package xml

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class HtmlTest extends AnyFreeSpec with Matchers {
  "removeTags" - {
    import Html.removeTags
    "1" in {
      removeTags("hi <br /> ho") shouldBe "hi ho"
    }
    "2" in {
      removeTags("<b>hi</b><i>ho</hi>") shouldBe "hi ho"
    }
    "3" in {
      removeTags("""<b><span id="42">hi</span>ho</b>""") shouldBe "hi ho"
    }
    "4" in {
      removeTags("hi\nho") shouldBe "hi ho"
    }
  }
}
