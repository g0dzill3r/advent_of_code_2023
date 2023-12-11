package aoc2023.week1.day5

import aoc2023.util.AOC

/**
 * Solution for day 5, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        var tmp = this.seeds2
        tmp = this.seedsToSoil.map (tmp)
        tmp = this.soilToFertilizer.map (tmp)
        tmp = this.fertilizerToWater.map (tmp)
        tmp = this.waterToLight.map (tmp)
        tmp = this.lightToTemperature.map (tmp)
        tmp = this.temperatureToHumidity.map (tmp)
        tmp = this.humidityToLocation.map (tmp)
        println (tmp.intervals.minOf { it.start })
    }

    return
}

// EOF