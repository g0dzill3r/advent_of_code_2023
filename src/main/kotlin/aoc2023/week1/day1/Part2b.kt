package aoc2023.week1.day1

import aoc2023.util.AOC
import aoc2023.util.firstLast
import aoc2023.util.replaceAll

/**
 * Day 1, part 2.
 */

fun main (args: Array<String>) {
    val (_, sample, actual) = AOC.getInputs (DAY)
    println ("Sample: ${solve (sample)}")
    println ("Actual: ${solve (actual)}")
    return
}

private fun solve (rows: List<String>): Int {
    return rows.map {
        it.replaced
    }.map {
        it.filter { it.isDigit () }
    }.map {
        it.firstLast
    }.map {
        it.toInt ()
    }.foldRight (0) { next, acc ->  next + acc }
}

private val String.replaced: String
    get () = this.replaceAll (REPLACE2)


val REPLACE2 = listOf (
    "one" to "o1e",
    "two" to "t2o",
    "three" to "th3ee",
    "four" to "fo4r",
    "five" to "f5ve",
    "six" to "s6x",
    "seven" to "se7en",
    "eight" to "ei8ht",
    "nine" to "n9ne"
)
// EOF