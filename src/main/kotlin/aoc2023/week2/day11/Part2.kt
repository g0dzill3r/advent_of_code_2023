package aoc2023.week2.day11

import aoc2023.util.AOC
import java.math.BigInteger

/**
 * Solution for day 11, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(DAY)

    val galaxies = Parser.parse(inputs.actual)
    galaxies.dump()

    // Find the number of empty rows and columns that will be subject to expansion

    val (rows, cols) = galaxies.grid.empty ()

    // Get the pairs of all galaxies that we need to calculate the distance between

    val expansion = 1_000_000
    val pairs = galaxies.pairs

    // Calculate the distances, taking into consideration the expansion factors

    var total = 0.toBigInteger ()
    pairs.forEach { (a, b) ->
        val extraRows = (expansion - 1) * rows.clamp (a.row, b.row).count ()
        val extraCols = (expansion - 1) * cols.clamp (a.col, b.col).count ()
        val distance = distance (a, b) + extraRows + extraCols
        total += distance.toBigInteger()
    }
    println (total)
    return
}

fun List<Int>.clamp (a: Int, b: Int): List<Int> {
    return if (a < b) {
        this.filter { it in (a + 1)..<b }
    } else {
        this.filter { it in (b + 1)..<a }
    }
}

// EOF