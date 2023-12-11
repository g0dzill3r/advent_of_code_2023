package aoc2023.week1.day7.part1

import aoc2023.util.AOC

/**
 * Solution to day 7, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse(inputs.actual).apply {
        val sorted = rounds.sortedWith (Round.comparator)
        val total = sorted.foldRightIndexed (0.toBigInteger ()) { i, next, acc ->
            acc + (next.bid * (i + 1)).toBigInteger()
        }
        println (total)
    }

    return
}

// EOF