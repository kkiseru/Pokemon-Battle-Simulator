package my.com.seru.view

import my.com.seru.MainApp
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class AttackerPokemonController(private val DialogueImage: ImageView,
                                private var dialogue: Text,
                                private var pokeball1: Button,
                                private var pokeball2: Button,
                                private var pokeball3: Button) {

  private var clickCount = 0
  private var imageClickedCount = 0

  // Initialize button click handlers
  pokeball1.onAction = _ => handleButtonClick()
  pokeball2.onAction = _ => handleButtonClick()
  pokeball3.onAction = _ => handleButtonClick()

  dialogue.font = MainApp.getCustomFont
  dialogue.layoutX = 100
  dialogue.layoutY = 478
  dialogue.text = "Welcome Trainer!\n\nPlease Choose 2 Pokemon by clicking twice on the pokeball!"

  DialogueImage.visible = false

  private def handleButtonClick(): Unit = {
    dialogue.layoutX = 100
    dialogue.layoutY = 500
    clickCount += 1
    if (clickCount >= 1) {
      dialogue.text = "You have selected a pokemon, please select another."
    }
    if (clickCount >= 2) {
      dialogue.text = "Congratulations, You picked Rowlet and Blastoise!            >"
      DialogueImage.visible = true
    }
  }

  // Set up the event handler for the DialogueImage
  DialogueImage.onMouseClicked = (event: MouseEvent) => handleDialogueImageClick()

  private def handleDialogueImageClick(): Unit = {
    imageClickedCount += 1
    if (imageClickedCount == 1) {
      dialogue.text = "You will now proceed to select the area to battle.            >"
    } else {
      MainApp.showChoosePokemon()
    }
  }
}
