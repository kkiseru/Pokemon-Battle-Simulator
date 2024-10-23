package my.com.seru.view

import scalafx.Includes._
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{AnchorPane, StackPane}
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafxml.core.macros.sfxml
import my.com.seru.MainApp

@sfxml
class ChoosePokemonController (
                               private var iceberg: ImageView,
                               private var bush: ImageView,
                               private var rootPane: AnchorPane
                             ) {

  private var mediaPlayer: MediaPlayer = _

  iceberg.onMouseClicked = handleIcebergClick _
  bush.onMouseClicked = handleBushClick _

  // Initialize the music
  LoadMedia("/sfx/SelectionPageMusic.mp3", false)

  def LoadMedia(filePath: String, isVideo: Boolean): Unit = {
    val mediaFile = getClass.getResource(filePath)
    val media = new Media(mediaFile.toString)

    if (isVideo) {
      // Play video
      val mediaPlayer = new MediaPlayer(media)
      val videoView = new MediaView(mediaPlayer)
      videoView.fitWidth <== rootPane.width
      videoView.fitHeight <== rootPane.height
      videoView.preserveRatio = false
      val videoPane = new StackPane()
      videoPane.children.add(videoView)
      rootPane.children.add(videoPane)
      rootPane.layout() // Update the layout to include the videoPane
      mediaPlayer.play()

      // Handle the end of the video
      mediaPlayer.onEndOfMedia = () => {
        rootPane.children.remove(videoPane)
        mediaPlayer.stop()
        // Call showBattleSelection with selected attacker and defender IDs
        MainApp.showBattleSelection()
      }
    } else {
      // Play music
      mediaPlayer = new MediaPlayer(media)
      mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
      mediaPlayer.play()
    }
  }

  def handleIcebergClick(event: MouseEvent): Unit = {
    mediaPlayer.stop()
    LoadMedia("/videos/KyogreVideo.mp4", true)
    PokemonIdManager.defenderId = 3
  }

  def handleBushClick(event: MouseEvent): Unit = {
    mediaPlayer.stop()
    LoadMedia("/videos/GroudonVideo.mp4", true)
    PokemonIdManager.defenderId =2
  }
}
