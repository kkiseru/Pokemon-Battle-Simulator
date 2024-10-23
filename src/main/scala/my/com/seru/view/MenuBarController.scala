package my.com.seru.view

import my.com.seru.MainApp
import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Menu, MenuItem}
import scalafxml.core.macros.sfxml

@sfxml
class MenuBarController {
  def exitGame(): Unit = {
    Platform.exit()
  }
  def showMainPage(): Unit = {
    MainApp.showStartView()
  }
  def showRank(): Unit = {
    MainApp.showScore()
  }
  def showAbout(): Unit = {
    new Alert(AlertType.Information){
      initOwner(MainApp.stage)
      title       = "About"
      headerText  = "About"
      contentText = "This game and visuals are drawn and made made by Seru" +
        "\nHowever the some of the materials provided here do not belong to me, and I do not claim ownership or copyright over them. The material is used for educational and illustrative purposes, and any intellectual property rights, including copyrights, are retained by the original authors or entities. If you intend to use or distribute the content, it is essential to seek permission from the rightful owners and comply with any relevant legal requirements."
    }.showAndWait()
  }


}
