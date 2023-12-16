package aoc2023.week3.day16

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

val DAY = 16

/**
 * Solution to day 16, part 1.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.actual)
    val start = Beam (Direction.RIGHT, Coordinate2 (0, -1))
    grid.bounce (start)
    println ("energized: ${grid.energizedCount}")
    return
}

// EOF