package aoc2023.week2.day8

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        var current = nodes.keys.filter { it.endsWith ('A') }
        val turns = turns
        var steps = 0

        println ("starts: $current")

        while (true) {
            val done = current.all { it.endsWith ('Z') }
            println (current)
            if (done) {
                break
            }

            val nextTurn = turns.next ()
            println ("TURN ${nextTurn}")
            current = current.map { label ->
                val node = nodes[label] as Node
                val move = when (nextTurn) {
                    Direction.LEFT -> node.leftLabel
                    Direction.RIGHT -> node.rightLabel
                }
                println (" - ${label} ${node.leftLabel}/${node.rightLabel} -> $move")
                move
            }
            Thread.sleep (1000)
            steps ++
        }

        println (steps)
    }

    return
}

// EOF