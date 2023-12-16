package aoc2023.week3.day16

import aoc2023.util.AOC

val DAY = 16

/**
 * Solution to day 16, part 1.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.actual)
    grid.bounce ()
    println ("energized: ${grid.energizedCount}")
    return
}

// EOF