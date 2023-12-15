package aoc2023.week2.day14

import aoc2023.util.AOC

val DAY = 14

/**
 * Solution to day 14, part 1.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.actual)
    grid.tilt (Direction.NORTH)

    var total = 0
    grid.map { coord ->
        if (grid.getTile (coord) == Rock.ROUNDED) {
            total += grid.height - coord.row
        }
    }
    println (total)
    return
}

// EOF