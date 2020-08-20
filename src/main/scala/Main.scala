object Main extends App {
  run()

  private def run(): Unit = {
    osmnotes.run()
    mailinglist.run()
    mastodon.run()
    wiki.run()
  }
}
