package aoc2023.week3.day15

import aoc2023.util.AOC

val DAY = 15

/**
 * Solution to day 15, part 1.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val els = Parser.parse (inputs.actual)
    var total = 0
    els.forEach {
        val hash = hash (it.toString ())
        println ("$it - ${hash}")
        total += hash
    }
    println (total)
    return
}

// EOF