package aoc2023.week2.day13

import aoc2023.util.AOC

val DAY = 13

/**
 * Solution to day 13, part 1.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val patterns = Parser.parse (inputs.actual)
    var total = 0
    patterns.grids.forEach {
        val (horizontal, vertical) = it.seams
        if (vertical != null) {
            total += vertical + 1
        } else if (horizontal != null) {
            total += 100 * (horizontal + 1)
        } else {
            it.dump ()
            throw Exception ("No relection.")
        }
    }
    println (total)
    return
}

// EOF