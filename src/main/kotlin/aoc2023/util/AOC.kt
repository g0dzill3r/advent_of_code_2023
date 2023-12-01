package aoc2023.util

/**
 * Advent of Code utility functions.
 */

object AOC {
    val SAMPLE = 0
    val ACTUAL = 1

    /**
     * Get the two input files for the daily puzzle.
     */

    fun getInputs (day: Int): List<List<String>> {
        val path = "week${day / 7 + 1}/day${day}"
        val sample = ResourceUtil.getAsString ("$path/sample.txt").split ("\n")
        val puzzle = ResourceUtil.getAsString ("$path/puzzle.txt").split ("\n")
        return listOf (sample, puzzle)
    }
}

// EOF