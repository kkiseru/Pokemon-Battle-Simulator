package my.com.seru.view

import my.com.seru.MainApp
import my.com.seru.model.Player
import scalafx.scene.control.{TableColumn, TableView}
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.scene.text.{Font, Text}
import scalafxml.core.macros.sfxml

@sfxml
class ScoreController (
                        private val HighScoreTable: TableView[Player],
                        private val UsernameColumn: TableColumn[Player, String],
                        private val ScoreColumn: TableColumn[Player, Int],
                        private val scoreText: Text
                      ) {

  UsernameColumn.cellValueFactory = { cellData => StringProperty(cellData.value.userName.value) }
  ScoreColumn.cellValueFactory = { cellData => ObjectProperty(cellData.value.score.value) }

  HighScoreTable.items = MainApp.Name

  applyCustomFont()

  private def applyCustomFont(): Unit = {
    val customFont: Font = MainApp.getCustomFont
    if (customFont == null) {
      println("Custom font is null")
    }
    if (scoreText == null) {
      println("scoreText is null")
    } else {
      scoreText.font = customFont
    }
  }
}
