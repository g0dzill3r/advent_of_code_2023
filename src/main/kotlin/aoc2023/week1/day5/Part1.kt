package aoc2023.week1.day5

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        val locations = this.seeds.map { it.soil.fertilizer.water.light.temperature.humidity.location.id }
        println (locations.min ())
    }

    return
}

// EOF