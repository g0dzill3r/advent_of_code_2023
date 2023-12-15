package aoc2023.week2.day13

import aoc2023.util.AOC

/**
 * Solution to day 13, part 2.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val patterns = Parser.parse (inputs.actual)

    patterns.grids.forEach { grid ->
        grid.dump ()
        println (grid.seams)
//        println (grid.alternate)
        println ()
    }
    return
}

// EOF