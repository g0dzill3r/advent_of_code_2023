package aoc2023.week1.day1

import aoc2023.util.AOC
import aoc2023.util.firstLast

val DAY = 1

/**
 * Day 1 puzzle.
 */

fun main (args: Array<String>) {
    val (sample, _, actual) = AOC.getInputs(DAY)
    println ("Sample: ${solve (sample)}")
    println ("Actual: ${solve (actual)}")
    return
}


private fun solve (rows: List<String>): Int {
    return rows.map {
        it.filter { it.isDigit () }
    }.map {
        it.firstLast
    }.map {
        it.toInt()
    }.foldRight (0) { next, acc ->  next + acc }
}

// EOF