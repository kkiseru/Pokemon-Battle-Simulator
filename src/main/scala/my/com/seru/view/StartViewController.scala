package my.com.seru.view

import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.media.{Media, MediaPlayer}
import scalafxml.core.macros.sfxml
import my.com.seru.MainApp

@sfxml
class StartViewController(private val startbutton: Button) {
  startbutton.font = MainApp.getCustomFont
  private var mediaPlayer: MediaPlayer = _
    def loadMedia(filePath: String): Unit = {
      val mediaFile = getClass.getResource(filePath)
      val media = new Media(mediaFile.toString)
      mediaPlayer = new MediaPlayer(media)
      mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
      mediaPlayer.play()
    }
  loadMedia("/sfx/startmusic.mp3")
  def pressedStartButton(): Unit = {
    mediaPlayer.stop()
    MainApp.showAttackerPokemon()
  }
}
