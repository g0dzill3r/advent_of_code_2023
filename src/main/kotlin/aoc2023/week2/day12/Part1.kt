package aoc2023.week2.day12

import aoc2023.util.AOC

val DAY = 12

/**
 * Solution for day 12, part 1
 */

fun main () {
    val inputs = AOC.getInputs(DAY)
    val records = Parser.parse (inputs.sample1)
    val result = records.records.foldRight (0) { next, acc -> acc + next.arrangements }
    println (result)
    return
}

// EOf