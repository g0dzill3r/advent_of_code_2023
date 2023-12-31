package aoc2023.week2.day11

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2
import kotlin.math.abs

val DAY = 11

/**
 * Solution for day 11, part 1.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    val galaxies = Parser.parse (inputs.actual)
    galaxies.dump ()

    galaxies.grid.expand (Tile.SPACE)
    galaxies.dump ()

    // Find all the galaxies

    val els = galaxies.all
    println ("Found ${els.size} galaxies.")

    // Get all the pairs of galaxies to compute the distance between

    val pairs = galaxies.pairs
    var total = 0
    pairs.forEach {
        val (a, b) = it
        total += distance (a, b)
    }
    println (total)
    return
}

fun distance (a: Coordinate2, b: Coordinate2): Int {
    return abs (a.row - b.row) + abs (a.col - b.col)
}

// EOF