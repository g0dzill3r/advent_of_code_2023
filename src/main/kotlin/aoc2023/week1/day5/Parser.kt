package aoc2023.week1.day5

import aoc2023.util.AOC
import aoc2023.util.BigIntegerRange
import aoc2023.util.PeekableIterator
import aoc2023.util.peekable
import java.io.IOException
import java.math.BigInteger
import java.util.regex.Pattern

val DAY = 5

data class Almanac (
    val _seeds: List<BigInteger>,
    val seedsToSoil: Mappings,
    val soilToFertilizer: Mappings,
    val fertilizerToWater: Mappings,
    val waterToLight: Mappings,
    val lightToTemperature: Mappings,
    val temperatureToHumidity: Mappings,
    val humidityToLocation: Mappings
) {
    val seeds: List<Seed>
        get () = _seeds.map { Seed (it, this) }

    val seeds2: Intervals
        get () {
            val intervals = mutableListOf<Interval> ()
            val iter = _seeds.iterator ()
            while (iter.hasNext ()) {
                val start = iter.next ()
                val length = iter.next ()
                intervals.add (Interval (start, length))
            }
            return Intervals (intervals)
        }
}

data class Seed (
    val id: BigInteger,
    val almanac: Almanac
) {
    val soil: Soil
        get () = Soil (almanac.seedsToSoil.map (id), almanac)
}

data class Soil (
    val id: BigInteger,
    val almanac: Almanac
) {
    val fertilizer: Fertilizer
        get () = Fertilizer (almanac.soilToFertilizer.map (id), almanac)
}

data class Fertilizer (
    val id: BigInteger,
    val almanac: Almanac
) {
    val water: Water
        get () = Water (almanac.fertilizerToWater.map (id), almanac)
}

data class Water (
    val id: BigInteger,
    val almanac: Almanac
) {
    val light: Light
        get () = Light (almanac.waterToLight.map (id), almanac)
}

data class Light (
    val id: BigInteger,
    val almanac: Almanac
) {
    val temperature: Temperature
        get () = Temperature (almanac.lightToTemperature.map (id), almanac)
}

data class Temperature (
    val id: BigInteger,
    val almanac: Almanac
) {
    val humidity: Humidity
        get () = Humidity (almanac.temperatureToHumidity.map (id), almanac)
}

data class Humidity (
    val id: BigInteger,
    val almanac: Almanac
) {
    val location: Location
        get () = Location (almanac.humidityToLocation.map (id), almanac)
}

data class Location (
    val id: BigInteger,
    val BigInteger: Almanac
)

data class Mappings (
    val mappings: List<Mapping>
) {
    /**
     * Remap a single point.
     */

    fun map (from: BigInteger): BigInteger {
        for (mapping in mappings) {
            if (mapping.contains (from)) {
                return mapping.dest + from - mapping.source
            }
        }
        return from
    }

    /**
     * Remap a set of intervals
     */

    fun map (intervals: Intervals): Intervals {
        println ("map")

        // Determine all the places where the intervals need to break to match the explicit
        // mappings intervals.

        val breaks = mutableListOf<BigInteger> ().apply {
            mappings.forEach {
                add(it.source)
                add(it.source + it.length)
            }
        }

        // Take the source intervals and break them to match the boundaries of the mappings

        var tmp = intervals.sort ().coalesce ().split (breaks).intervals

        // Now remap the intervals into the new domain

        tmp = tmp.map {
            map (it)
        }

        return Intervals (tmp)
    }

    private fun find (value: BigInteger): Mapping? {
        for (mapping in mappings) {
            if (mapping.contains (value)) {
                return mapping
            }
        }
        return null
    }

    fun map (interval: Interval): Interval {
        val mapping = find (interval.start)
        return if (mapping != null) {
            val offset = interval.start - mapping.source
            Interval (mapping.dest + offset, interval.length)
        } else {
            interval
        }
    }
}

data class Mapping (
    val dest: BigInteger,
    val source: BigInteger,
    val length: BigInteger,
) {
    constructor (dest: Int, source: Int, length: Int) : this (dest.toBigInteger(), source.toBigInteger(), length.toBigInteger())

    fun contains (value: BigInteger): Boolean {
         return value >= source && value < (source.plus (length))
    }
}

object Parser {
    fun parse (rows: List<String>): Almanac {
        val iter = rows.iterator().peekable()
        var seeds: List<BigInteger>? = null
        var seedsToSoil: Mappings? = null
        var soilToFertilizer: Mappings? = null
        var fertilizerToWater: Mappings? = null
        var waterToLight: Mappings? = null
        var lightToTemperature : Mappings? = null
        var temperatureToHumidity: Mappings? = null
        var humidityToLocation: Mappings? = null

        while (iter.hasNext ()) {
            val next = iter.peek ()
            when {
                next.isEmpty() -> iter.next ()
                next.startsWith (Tags.SEEDS) -> seeds = parseSeeds (iter)
                next.startsWith (Tags.SEEDS_TO_SOIL) -> seedsToSoil = parseMappings (iter)
                next.startsWith (Tags.SOIL_TO_FERTILIZER) ->  soilToFertilizer = parseMappings (iter)
                next.startsWith (Tags.FERTILIZER_TO_WATER) ->  fertilizerToWater = parseMappings (iter)
                next.startsWith (Tags.WATER_TO_LIGHT) ->  waterToLight = parseMappings (iter)
                next.startsWith (Tags.LIGHT_TO_TEMPERATURE) ->  lightToTemperature = parseMappings (iter)
                next.startsWith (Tags.TEMPERATURE_TO_HUMIDITY) ->  temperatureToHumidity = parseMappings (iter)
                next.startsWith (Tags.HUMIDITY_TO_LOCATION) ->  humidityToLocation = parseMappings (iter)
                else -> throw IOException ("Unrecognized tag: $next")
            }
        }

        return Almanac (
            seeds!!,
            seedsToSoil!!,
            soilToFertilizer!!,
            fertilizerToWater!!,
            waterToLight!!,
            lightToTemperature!!,
            temperatureToHumidity!!,
            humidityToLocation!!
        )
    }

    private fun parseNumbers (str: String): List<BigInteger> = str.trim ().split (Pattern.compile ("\\s+")).map { it.toBigInteger() }

    fun parseMappings (iter: PeekableIterator<String>): Mappings {
        iter.next ();
        val mappings = mutableListOf<Mapping>().apply {
            while (iter.hasNext () && iter.peek ().isNotEmpty()) {
                val next = iter.next ()
                val (source, dest, len) = parseNumbers (next)
                add (Mapping (source, dest, len))
            }
        }
        mappings.sortBy { it.source }
        return Mappings (mappings)
    }

    fun parseSeeds (iter: Iterator<String>): List<BigInteger> {
        val next = iter.next ()
        val i = next.indexOf (":")
        return parseNumbers (next.substring (i + 1))
    }
}

object Tags {
    val SEEDS = "seeds"
    val SEEDS_TO_SOIL = "seed-to-soil"
    val SOIL_TO_FERTILIZER = "soil-to-fertilizer"
    val FERTILIZER_TO_WATER = "fertilizer-to-water"
    val WATER_TO_LIGHT = "water-to-light"
    val LIGHT_TO_TEMPERATURE = "light-to-temperature"
    val TEMPERATURE_TO_HUMIDITY = "temperature-to-humidity"
    val HUMIDITY_TO_LOCATION = "humidity-to-location"
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(DAY)
    val almanac = Parser.parse (inputs.sample1)

    almanac.seeds.forEach {
        println ("seed ${it.id} is location ${it.soil.fertilizer.water.light.temperature.humidity.location.id}")
    }

    return
}

// EOF