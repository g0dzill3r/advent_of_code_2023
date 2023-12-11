package aoc2023.week2.day8

import aoc2023.util.AOC

/**
 * Solution to day 8, part 1.
 */

private val START = "AAA"
private val END = "ZZZ"

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.sample1).apply {
        val start = nodes[START]!!
        val end = nodes[END]!!

        var current = start
        var steps = 0
        val turns = turns

        while (current != end) {
            val nextTurn = turns.next ()
            current = when (nextTurn) {
                Direction.LEFT -> current.left
                Direction.RIGHT -> current.right
            }
            steps ++
        }

        println (steps)
    }
    return
}

// EOF