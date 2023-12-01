package aoc2023.week1.day1

import aoc2023.util.AOC

/**
 * Day 1 puzzle.
 */

fun main (args: Array<String>) {
    val (sample, actual) = AOC.getInputs(1)
    println ("Sample: ${solve (sample)}")
    println ("Actual: ${solve (actual)}")
    return
}

val String.firstLast: String
    get () = "${this[0]}${this[this.length - 1]}"

fun solve (rows: List<String>): Int {
    return rows.map {
        it.filter { it.isDigit () }
    }.map {
        it.firstLast
    }.map {
        it.toInt()
    }.foldRight (0) { next, acc ->  next + acc }
}

// EOF