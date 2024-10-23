//package my.com.seru.view
//
//import my.com.seru.MainApp
//import scalafx.Includes._
//import scalafx.scene.text.{Font, Text}
//import scalafx.scene.control.{Button, ButtonBar, ProgressBar}
//import scalafxml.core.macros.sfxml
//import my.com.seru.model.Pokemon
//
//@sfxml
//class BattleViewController(
//                            private var PokemonDefenderName: Text,
//                            private var HPDefender: Text,
//                            private var DefHPBar: ProgressBar,
//                            private var PokemonAttackerName: Text,
//                            private var HPAttacker: Text,
//                            private var AtkHPBar: ProgressBar,
//                            private var HP: Text,
//                            private var text: Text,
//                            private var fightButton: Button,
//                            private var runButton: Button,
//                            private var movesets: ButtonBar,
//                            private var move1: Button,
//                            private var move2: Button,
//                            private var move3: Button,
//                            private var move4: Button
//                          ) {
//  text.text = "Please Select Your Move"
//  private var currentDefender: Option[Pokemon] = None
//  private var currentAttacker: Option[Pokemon] = None
//
//  // Initialize button actions
//  fightButton.onAction = _ => handleFight()
//  runButton.onAction = _ => handleRun()
//  move1.onAction = _ => handleMove(move1.text.value)
//  move2.onAction = _ => handleMove(move2.text.value)
//  move3.onAction = _ => handleMove(move3.text.value)
//  move4.onAction = _ => handleMove(move4.text.value)
//
//  // Initialize move buttons' visibility
//  movesets.visible = false
//  text.text = "Please Select Your Move"
//
//  def handleFight(): Unit = {
//    fightButton.visible = false
//    runButton.visible = false
//    movesets.visible = true
//    text.text = "Please Select Your Move"
//  }
//
//  def handleRun(): Unit = {
//    MainApp.showChoosePokemon()
//  }
//
//  def handleMove(moveName: String): Unit = {
//    (currentAttacker, currentDefender) match {
//      case (Some(attacker), Some(defender)) =>
//        // Find the move's damage
//        val moveDamage = attacker.pokemove match {
//          case `moveName` => attacker.pokemovedamage
//          case _ => 0
//        }
//
//        // Update defender's HP
//        val newDefenderHp = defender.pokehp - moveDamage
//        DefHPBar.progress = newDefenderHp.toDouble / defender.pokehp
//        HPDefender.text = s"HP: $newDefenderHp"
//
//        // Check if the defender is defeated
//        if (newDefenderHp <= 0) {
//          text.text = "Defender Pokémon defeated!"
//          // Additional logic for when defender is defeated
//        }
//      case _ => text.text = "Error: Missing attacker or defender Pokémon"
//    }
//  }
//
//  def initializeWithAttacker(attackerId: Int, defenderId: Int): Unit = {
//    currentAttacker = Pokemon.attackers.find(_.pokeid == attackerId)
//    currentDefender = Pokemon.defenders.find(_.pokeid == defenderId)
//
//    currentAttacker.foreach { attacker =>
//      PokemonAttackerName.text = attacker.pokename
//      HPAttacker.text = s"HP: ${attacker.pokehp}"
//      AtkHPBar.progress = attacker.pokehp.toDouble / attacker.pokehp
//
//      // Set move buttons
//      val moves = List(attacker.pokemove) // Adjust if there are multiple moves
//      move1.text = moves.headOption.getOrElse("")
//      move2.text = moves.drop(1).headOption.getOrElse("")
//      move3.text = moves.drop(2).headOption.getOrElse("")
//      move4.text = moves.drop(3).headOption.getOrElse("")
//    }
//
//    currentDefender.foreach { defender =>
//      PokemonDefenderName.text = defender.pokename
//      HPDefender.text = s"HP: ${defender.pokehp}"
//      DefHPBar.progress = defender.pokehp.toDouble / defender.pokehp
//    }
//  }
//}
