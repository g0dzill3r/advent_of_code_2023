package aoc2023.week2.day14

import aoc2023.util.AOC
import aoc2023.util.increment
import aoc2023.util.repeat

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.actual)

    // Repeat enough times that we're likely to get a repeating pattern

    val totals = mutableListOf<Int> ()
    1000.repeat {
        grid.tilt(Direction.NORTH)
        grid.tilt(Direction.WEST)
        grid.tilt(Direction.SOUTH)
        grid.tilt(Direction.EAST)

        var total = 0
        grid.map { coord ->
            if (grid.getTile (coord) == Rock.ROUNDED) {
                total += grid.height - coord.row
            }
        }
        totals.add (total)
    }

    // Find the repeating cycles that should appear in the totals

    val pattern = findPattern (totals)
    val repeating = totals.subList (pattern.first, pattern.first + pattern.second)

    val target = 1_000_000_000
    val pick = (target - pattern.first  - 1) % pattern.second
    println (repeating [pick])
    return

}

/**
 * Looks in a series of values for a repeating sequence that is likely to
 * go on forever.
 *
 * We're not going to be exhaustive here and assume that the edge cases
 * are unlikely to occur.
 */

fun findPattern (seq: List<Int>): Pair<Int, Int> {
    var span = 2

    while (true) {
        val first = seq.subList (seq.size - span, seq.size)
        val second = seq.subList (seq.size - 2 * span, seq.size - span)
        if (first == second) {
            return seq.size - span to span
        }
        span ++
    }

    // NOT REACHED
}

// EOF