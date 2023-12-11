package aoc2023.week1.day2

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val (s1, _, p) = AOC.getInputs (2)

    val limit = Limit (12, 13, 14)

    Parser.parse (s1).let {
        println ("sample: ${possible (it, limit)}")
    }

    Parser.parse (p).let {
        println ("actual: ${possible (it, limit)}")
    }
    return
}

data class Limit (
    val red: Int,
    val green: Int,
    val blue: Int
)

private fun possible (games: List<Game>, limit: Limit): Int {
    var total = 0
    games.forEach { game ->
        if (game.possible (limit)) {
            total += game.id
        }
    }
    return total
}

// EOF