package aoc2023.week1.day6

import aoc2023.util.AOC

/**
 * Solution to day 6, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        var total = 1;
        races.forEach {
            val outcomes = it.outcomes.filter { (time, distance) ->
                distance > it.distance
            }
            val count = outcomes.count ()
            total *= count
        }
        println (total)
    }
    return
}

// EOF