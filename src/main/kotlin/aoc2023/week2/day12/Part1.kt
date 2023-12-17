package aoc2023.week2.day12

import aoc2023.util.AOC

val DAY = 12

/**
 * Solution for day 12, part 1
 */

fun main () {
    val inputs = AOC.getInputs(DAY)
    val records = Parser.parse (inputs.actual)

    var total = 0
    records.records.forEach { record ->
        val permutations = permutations(record.unknowns)
        val possible = permutations.filter { record.replaced(it).isValid }.count()
        total += possible
    }
    println (total)
    return
}

/**
 * Calculate all the possible permutations of certain conditions we could
 * use to replace unknowns.
 */

fun permutations (count: Int): List<List<Condition>> {
    return when (count) {
        1 -> listOf (listOf (Condition.DAMAGED), listOf (Condition.OPERATIONAL))
        else -> {
            val sublist = permutations (count - 1)
            mutableListOf<List<Condition>> ().apply {
                sublist.forEach { list ->
                    Condition.known.forEach { condition ->
                        add (mutableListOf<Condition>().apply {
                            addAll (list)
                            add (condition)
                        })
                    }
                }
            }
        }
    }
}

// EOf