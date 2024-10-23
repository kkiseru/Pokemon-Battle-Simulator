package my.com.seru.view

import my.com.seru.MainApp
import my.com.seru.MainApp.Name
import my.com.seru.model.Player.userName
import scalafx.application.Platform
import scalafx.scene.control.{Button, ButtonBar, ProgressBar}
import scalafx.scene.text.{Font, Text}
import scalafxml.core.macros.sfxml
import my.com.seru.model.{Player, Pokemon}
import scalafx.Includes._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.AnchorPane
import scalafx.scene.image.ImageView
import scalafx.event.ActionEvent
import scalafx.scene.media.{Media, MediaPlayer}

@sfxml
class BattleSelectionController(
                                 private var PokemonDefenderName: Text,
                                 private var HP: Text,
                                 private var HPDefender: Text,
                                 private var DefHPBar: ProgressBar,
                                 private var PokemonAttackerName: Text,
                                 private var AtkHP: Text,
                                 private var HPAttacker: Text,
                                 private var AtkHPBar: ProgressBar,
                                 private var fightButton: Button,
                                 private var runButton: Button,
                                 private var movesets: ButtonBar,
                                 private var text: Text,
                                 private var move1: Button,
                                 private var move2: Button,
                                 private var move3: Button,
                                 private var move4: Button,
                                 private var BattleDialog: ImageView,
                                 private var Battle: AnchorPane,
                                 private var Dialogue: Text,
                                 private var Kyogre: ImageView,
                                 private var Groudon: ImageView,
                                 private var Rowlet: ImageView,
                                 private var Blastoise: ImageView
                               ) {

  private var currentDefenderHp: Double = 0.0
  private var maxDefenderHp: Double = 0.0

  private var currentAttackerHp: Double = 0.0
  private var maxAttackerHp: Double = 0.0

  private var attackerId: Int = 0
  private var attackCount: Int = 0
  private val maxAttacks: Int = 2
  private var attackerTurn: Boolean = true
  private var playerScore: Int = 0

  private val defenderId: Int = PokemonIdManager.defenderId
  private var mediaPlayer: MediaPlayer = _


  initialize()


  def loadMedia(filePath: String): Unit = {
    val mediaFile = getClass.getResource(filePath)
    val media = new Media(mediaFile.toString)
    mediaPlayer = new MediaPlayer(media)
    mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
    mediaPlayer.play()
  }

  // Wild Pokémon Battle - Pokémon Red/Blue/Yellow Soundtrack  https://youtu.be/LgK2f47q8cU?si=oVwnbYl5qKUxM9v_
  loadMedia("/sfx/BattleMusic.mp3")

  private def initialize(): Unit = {
    setDefenderData(defenderId)
    setAttackerData(attackerId)

    fightButton.onAction = (e: ActionEvent) => handleFight()
    runButton.onAction = (e: ActionEvent) => handleRun()

    movesets.visible = false
    move1.onAction = (e: ActionEvent) => handleMove(0)
    move2.onAction = (e: ActionEvent) => handleMove(1)
    move3.onAction = (e: ActionEvent) => handleMove(2)
    move4.onAction = (e: ActionEvent) => handleMove(3)
    BattleDialog.onMouseClicked = (e: MouseEvent) => handleBattleDialog()

    text.visible = true
    Dialogue.visible = false
    text.layoutX = 50.0
    text.layoutY = 100.0
    val defenderName = Pokemon.defenders.find(_.pokeId == defenderId).map(_.pokeName).getOrElse("Unknown Pokémon")
    text.text = s"A wild $defenderName appeared!"

    applyCustomFont()

    Rowlet.visible = attackerId == 0
    Blastoise.visible = attackerId != 0
    Groudon.visible = defenderId != 3
    Kyogre.visible = defenderId == 3
  }

  private def applyCustomFont(): Unit = {
    val customFont: Font = MainApp.getCustomFont
    PokemonDefenderName.font = customFont
    HPDefender.font = customFont
    PokemonAttackerName.font = customFont
    HPAttacker.font = customFont
    text.font = customFont
    move1.font = customFont
    move2.font = customFont
    move3.font = customFont
    move4.font = customFont
    fightButton.font = MainApp.getCustomFont
    runButton.font = MainApp.getCustomFont
    AtkHP.font = MainApp.getCustomFont
    HP.font = MainApp.getCustomFont
    Dialogue.font = MainApp.getCustomFont
  }

  private def handleFight(): Unit = {
    fightButton.visible = false
    runButton.visible = false
    movesets.visible = true
    text.visible = true
    text.layoutY = 50
    text.text = "Please Select Your Move"
  }

  private def handleRun(): Unit = {
    MainApp.showChoosePokemon()
  }

  private def handleBattleDialog(): Unit = {
    Battle.visible = true
    BattleDialog.visible = false
    Dialogue.visible = false
  }

  private def handleFaintDialog(): Unit = {
    Battle.visible = true
    BattleDialog.visible = false
    Dialogue.visible = false
    val playerName = PokemonIdManager.username
    val playerInstance = new Player(playerName, playerScore)
    playerInstance.score.value = playerScore
    Name += playerInstance
    Player.save()
    mediaPlayer.stop()
    MainApp.showScore()
  }

  private def displayBattleDialog(moveIndex: Int): Unit = {
    BattleDialog.visible = true
    val attackerMoves = Pokemon.attackers.filter(_.pokeId == attackerId)
    val move = attackerMoves(moveIndex)
    val attackerName = Pokemon.attackers.find(_.pokeId == attackerId).get.pokeName
    Battle.visible = false

    text.layoutY = 100.0
    Dialogue.visible = true
    Dialogue.text = s"$attackerName used ${move.pokeMove}"
  }

  private def setDefenderData(defenderId: Int): Unit = {
    val defender = Pokemon.defenders.find(_.pokeId == defenderId)
    defender match {
      case Some(defenderData) =>
        PokemonDefenderName.text = defenderData.pokeName
        HPDefender.text = s"${defenderData.pokeHp} HP"
        maxDefenderHp = defenderData.pokeHp.toDouble
        currentDefenderHp = maxDefenderHp
        updateProgressBar(DefHPBar, currentDefenderHp, maxDefenderHp)
      case None =>
        PokemonDefenderName.text = "Pokémon not found"
    }
  }

  private def setAttackerData(attackerId: Int): Unit = {
    val attackerMoves = Pokemon.attackers.filter(_.pokeId == attackerId)
    if (attackerMoves.nonEmpty) {
      val firstMove = attackerMoves.head
      PokemonAttackerName.text = firstMove.pokeName
      HPAttacker.text = s"${firstMove.pokeHp} HP"
      maxAttackerHp = firstMove.pokeHp.toDouble
      currentAttackerHp = maxAttackerHp
      updateProgressBar(AtkHPBar, currentAttackerHp, maxAttackerHp)

      move1.text = attackerMoves.lift(0).map(_.pokeMove).getOrElse("N/A")
      move2.text = attackerMoves.lift(1).map(_.pokeMove).getOrElse("N/A")
      move3.text = attackerMoves.lift(2).map(_.pokeMove).getOrElse("N/A")
      move4.text = attackerMoves.lift(3).map(_.pokeMove).getOrElse("N/A")
    } else {
      PokemonAttackerName.text = "Pokémon not found"
    }
  }

  private def updateProgressBar(progressBar: ProgressBar, currentHp: Double, maxHp: Double): Unit = {
    val progress = if (maxHp > 0) currentHp / maxHp else 0.0
    Platform.runLater {
      progressBar.progress = progress
    }
  }

  private def handleMove(moveIndex: Int): Unit = {
    val defenderName = Pokemon.defenders.find(_.pokeId == defenderId).map(_.pokeName).getOrElse("Unknown Pokémon")
    if (attackerTurn) {
      val attackerMoves = Pokemon.attackers.filter(_.pokeId == attackerId)
      val attackerName = Pokemon.attackers.find(_.pokeId == attackerId).get.pokeName

      if (attackerMoves.isDefinedAt(moveIndex)) {
        val move = attackerMoves(moveIndex)
        val damage = move.pokeMoveDamage

        val damageDealt = damage * 2

        currentDefenderHp -= damageDealt
        if (currentDefenderHp < 0) currentDefenderHp = 0

        playerScore += damageDealt

        updateProgressBar(DefHPBar, currentDefenderHp, maxDefenderHp)
        HPDefender.text = s"${currentDefenderHp.toInt} HP"
        displayBattleDialog(moveIndex)

        // Disable move buttons
        move1.disable = true
        move2.disable = true
        move3.disable = true
        move4.disable = true

        move1.disable = false
        move2.disable = false
        move3.disable = true
        move4.disable = true

        text.layoutX = 50.0
        text.layoutY = 50.0
        text.text = "Please Select Your Move"

        // Check if the defender's HP is 0 or less
        if (currentDefenderHp <= 0) {
          text.layoutY = 100
          text.text = s"$defenderName has fainted!"
          movesets.visible = false
          attackerTurn = false

          BattleDialog.onMouseClicked = (e: MouseEvent) => handleFaintDialog()
        } else {
          attackCount += 1
          if (attackCount >= maxAttacks) {
            attackCount = 0
            attackerTurn = false
            // Disable move buttons for attacker turn
            move1.disable = true
            move2.disable = true
            move3.disable = true
            move4.disable = true
            // Start defender's turn
            Platform.runLater {
              displayBattleDialog(moveIndex)
              Dialogue.text = s"$defenderName turn to counterattack"
              text.layoutY = 50
            }
            handleDefenderTurn()
          }
        }
      }
    }
  }

  private def handleDefenderTurn(): Unit = {
    val defenderName = Pokemon.defenders.find(_.pokeId == defenderId).map(_.pokeName).getOrElse("Unknown Pokémon")
    if (currentDefenderHp > 0) {
      // Simulate a defender move (for example, using the first move of the defender)
      val defenderMoveDamage = Pokemon.defenders.find(_.pokeId == defenderId)
        .map(_.pokeMoveDamage)
        .getOrElse(0)

      currentAttackerHp -= defenderMoveDamage
      updateProgressBar(AtkHPBar, currentAttackerHp, maxAttackerHp)
      HPAttacker.text = s"${currentAttackerHp.toInt} HP"

      if (currentAttackerHp <= 0) {
        text.text = s"$defenderName has fainted!"
        movesets.visible = false
        fightButton.visible = true
        runButton.visible = true

        BattleDialog.onMouseClicked = (e: MouseEvent) => handleFaintDialog()
      } else {
        // Prepare for the next round of attacks
        attackCount = 0
        move1.disable = false
        move2.disable = false
        move3.disable = false
        move4.disable = false
        attackerTurn = true
        text.text = "Please select your move"
      }
    }
  }
}
