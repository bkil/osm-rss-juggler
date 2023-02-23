import scala.util.control.NonFatal

object Main extends App {
  run()

  private def run(): Unit = {
    runIgnored(List(
      () => changesetdiscuss.run(),
      () => osmnotes.run(),
      () => mailinglist.run(),
      () => mastodon.run(),
      () => wiki.run(),
      () => gitlab.run(),
      () => wigle.run()
    ))
  }

  private def runIgnored(fs: List[() => Unit]): Unit = {
    fs.foreach { f =>
      try {
        f()
      } catch {
        case NonFatal(e) =>
          println(s"error: $e")
      }
    }
  }
}
