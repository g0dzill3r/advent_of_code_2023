package aoc2023.week1.day4

import aoc2023.util.AOC

/**
 * Solution to day 4, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).let {
        val result = it.cards.foldRight (0) { next, acc -> acc + next.score }
        println ("part1: $result")
    }

    return
}

// EOF