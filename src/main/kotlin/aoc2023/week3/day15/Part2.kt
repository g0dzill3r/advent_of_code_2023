package aoc2023.week3.day15

import aoc2023.util.AOC

/**
 * Solution to day 15, part 2.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val els = Parser.parse (inputs.actual)
    val boxes = Boxes ()

    els.forEach {
        boxes.process (it)
    }

    println (boxes.power)
    return
}

// EOF