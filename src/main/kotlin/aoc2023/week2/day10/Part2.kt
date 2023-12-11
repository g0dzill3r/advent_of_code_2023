package aoc2023.week2.day10

import aoc2023.util.AOC

/**
 * Solution to day 10, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        this.dump()
        val enclosed = this.enclosed
        enclosed.dump {
            when (it) {
                Nestable.UNKNOWN -> "?"
                Nestable.PIPE -> "■"
                Nestable.NO -> "•"
                Nestable.YES -> "O"
            }
        }

        println(enclosed.data.filter { it == Nestable.YES }.count())
    }
    return
}

// EOF