package aoc2023.week1.day3

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(3)

    Parser.parse (inputs.actual).let { schematic ->
        val symbols = schematic.symbols
        val numbers = schematic.numbers
        var total = 0
        symbols.forEach { symbol ->
            val adjacent = numbers.filter { part ->
                schematic.isAdjacent (part, symbol)
            }
            val count = adjacent.count ()
            if (count == 2) {
                val (p1, p2) = adjacent
                val gear = p1.value * p2.value
                total += p1.value * p2.value
            }
        }
        println (total)
    }
    return
}

// EOF