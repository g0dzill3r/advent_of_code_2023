package aoc2023.util

/**
 * Advent of Code utility functions.
 */

data class Inputs (
    val sample1: List<String>,
    val sample2: List<String>,
    val actual: List<String>,
    val test: List<String>?
)

val String.split: List<String>
    get () = this.split ("\n").map { it.trim () }

object AOC {
    val SAMPLE = 0
    val ACTUAL = 1

    private fun path (day: Int, which: String): String = "week${(day - 1) / 7 + 1}/day$day/$which.txt"

    fun getInputs (day: Int): Inputs {
        val path = "week${(day - 1) / 7 + 1}/day${day}"
        return Inputs (
            ResourceUtil.getAsString (path (day, "s1")).split,
            ResourceUtil.getAsString (path (day, "s2")).split,
            ResourceUtil.getAsString (path (day, "p")).split,
            absorb {
                ResourceUtil.getAsString (path (day, "t")).split
            }
        )
    }
}

// EOF