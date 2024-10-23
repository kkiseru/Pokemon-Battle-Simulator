package my.com.seru.model

case class Pokemon(pokeId: Int, pokeName: String, pokeType: String, pokeHp: Int, pokeMove: String, pokeMoveDamage: Int)

object Pokemon {
  // Attacker Pokémon Data
  private val attackerData = List(
    "0,Rowlet,Grass,300,Razor Leaf,30,Shadow Sneak,90,Brave Bird,120,Wave Crash,120",
    "1,Blastoise,Water,500,Tackle,50,Hydro Pump,90,Wave Crash,120,Wave Crash,120"
  )

  // Defender Pokémon Data
  private val defenderData = List(
    "2,Groudon,Ground,600,Precipice Blades,120",
    "3,Kyogre,Water,600,Origin Pulse,120"
  )

  // Method to parse Pokémon data into a list of `Pokemon` instances
  private def parsePokemon(data: String): List[Pokemon] = {
    val values = data.split(",")
    val id = values(0).toInt
    val name = values(1)
    val pType = values(2)
    val hp = values(3).toInt

    // Extract moves and their damages, there can be multiple moves
    val moves = (4 until values.length by 2).map { i =>
      val moveName = values(i)
      val moveDamage = values(i + 1).toInt
      Pokemon(id, name, pType, hp, moveName, moveDamage)
    }.toList

    moves
  }

  // Create lists of Pokémon for attackers and defenders
  val attackers: List[Pokemon] = attackerData.flatMap(parsePokemon)
  val defenders: List[Pokemon] = defenderData.flatMap(parsePokemon)
}
