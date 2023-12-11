package aoc2023.week1.day3

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(3)

    Parser.parse (inputs.actual).let { schematic ->
        val numbers = schematic.numbers
        var total = 0
        numbers.forEach {
            val count = schematic.surrounding(it)
                .map {
                    schematic.get (it.row, it.col)
                }.filter {
                    it is Element.SYMBOL
                }.count ()
            if (count > 0) {
                total += it.value
            }
        }
        println (total)
    }
    return
}

// EOF