package aoc2023.util

/**
 * Advent of Code utility functions.
 */

object AOC {
    /**
     * Get the two input files for the daily puzzle.
     */

    fun getInputs (day: Int): Pair<String, String> {
        val path = "week${day / 7 + 1}/day${day}"
        val sample = ResourceUtil.getAsString ("$path/sample.txt")
        val puzzle = ResourceUtil.getAsString ("$path/puzzle.txt")
        return Pair (sample, puzzle)
    }
}

// EOF