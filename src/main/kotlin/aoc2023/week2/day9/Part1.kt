package aoc2023.week2.day9

import aoc2023.util.AOC

/**
 * Solution to day 9, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    val parsed = Parser.parse (inputs.actual)
    val total = parsed.rows.foldRight (0) { next, acc ->
        acc + next.extrapolate
    }
    println (total)
    return
}

// EOF