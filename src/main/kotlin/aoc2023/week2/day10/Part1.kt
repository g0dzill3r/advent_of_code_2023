package aoc2023.week2.day10

import aoc2023.util.AOC

/**
 * Solution to day 10, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        println (distances.data.maxOf { it })
    }

    return
}

// EOF