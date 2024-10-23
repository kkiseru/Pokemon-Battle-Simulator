package my.com.seru

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import my.com.seru.model.Player
import my.com.seru.util.Database
import scalafx.collections.ObservableBuffer
import scalafx.scene.text.Font

object MainApp extends JFXApp {
  Database.setupDB()
  val Name = new ObservableBuffer[Player]()

  val rootResource = getClass.getResource("view/MenuBar.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  stage = new PrimaryStage {
    title = "Pokemon"
    scene = new Scene{
      root = roots
    }
  }
  private val customFont: Font = Font.loadFont(getClass.getResourceAsStream("/fonts/monaco-9.ttf"), 30)


  def loadScene(fxmlPath: String): Unit = {
    val resource = getClass.getResource(fxmlPath)
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)

  }

  def showBattleSelection(): Unit = {
    loadScene("/my/com/seru/view/BattleSelection.fxml")
  }

  def showAttackerPokemon(): Unit = {
    loadScene("/my/com/seru/view/AttackerPokemon.fxml")
  }

  def showChoosePokemon(): Unit = {
    loadScene("/my/com/seru/view/ChoosePokemon.fxml")
  }

  def showScore(): Unit = {
    loadScene("/my/com/seru/view/Score.fxml")
  }

  def showStartView(): Unit = {
    loadScene("/my/com/seru/view/StartView.fxml")
  }

  def getCustomFont: Font = customFont
  loadScene("/my/com/seru/view/StartView.fxml")
}
