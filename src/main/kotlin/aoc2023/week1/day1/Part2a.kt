package aoc2023.week1.day1

import aoc2023.util.AOC

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
        first(it) + last(it)
    }.map {
        it.toInt ()
    }.foldRight (0) { next, acc ->  next + acc }
}

fun find (row: String, range: IntProgression): Int {
    for (i in range) {
        if (row[i].isDigit ()) {
            return row[i] - '0'
        }
        for ((match, replace) in REPLACE) {
            if (row.indexOf (match) == i) {
                return replace
            }
        }
    }
    throw Exception ("No first digit.")
}

fun first (row: String): Int = find (row, row.indices)
fun last (row: String): Int = find (row, row.indices.reversed ())

val REPLACE = listOf (
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

// EOF