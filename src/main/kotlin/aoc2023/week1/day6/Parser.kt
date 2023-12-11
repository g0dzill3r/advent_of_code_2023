package aoc2023.week1.day6

import aoc2023.util.AOC
import java.util.regex.Pattern

val DAY = 6

data class Races (
    val races: List<Race>
)

data class Race (
    val time: Int,
    val distance: Int
) {
    val outcomes: List<Pair<Int, Int>>
        get () = (0 until time).map { Pair (it, score (time, it)) }

    companion object {
        fun score (time: Int, hold: Int): Int = (time - hold) * hold
    }
}

val String.numbers: List<Int>
    get () = this.trim ().split (Pattern.compile ("\\s+")).map { it.toInt () }

object Parser {
    fun parse (rows: List<String>): Races {
        if (rows.size != 2) {
            throw Exception ("Expected 2 rows; found ${rows.size}.")
        }
        val times = rows[0].substring (rows[0].indexOf (":") + 1).numbers
        val distances = rows[1].substring (rows[1].indexOf (":") + 1).numbers
        val races = mutableListOf<Race> ().apply {
            for (i in times.indices) {
                add (Race (times[i], distances[i]))
            }
        }
        return Races (races)
    }
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    val races = Parser.parse (inputs.sample1)
    println (races)

    val outcomes = Race (5, 0).outcomes
    println (outcomes)
    return
}

// EOF