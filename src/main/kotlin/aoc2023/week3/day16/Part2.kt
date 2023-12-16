package aoc2023.week3.day16

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

/**
 * Solution to day 16, part 2.
 */

fun main () {
    val inputs = AOC.getInputs(DAY)
    val grid = Parser.parse (inputs.actual)

    // Assemble a list of all the possible starting points

    val possibilities = mutableListOf<Beam> ().apply {
        for (row in 0 ..< grid.height) {
            add (Beam (Direction.RIGHT, Coordinate2 (row, -1)))
            add (Beam (Direction.LEFT, Coordinate2 (row, grid.width)))
        }
        for (col in 0 ..< grid.width) {
            add (Beam (Direction.DOWN, Coordinate2 (-1, col)))
            add (Beam (Direction.UP, Coordinate2 (grid.height, col)))
        }
    }

    // Calculate how many squares each one energizes

    val energized = possibilities.map  {
        grid.bounce (it)
    }
    val max = energized.max()
    println (max)
    return
}

// EOF