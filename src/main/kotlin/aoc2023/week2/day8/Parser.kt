package aoc2023.week2.day8

import aoc2023.util.AOC
import java.util.regex.Pattern

val DAY = 8

enum class Direction {
    LEFT, RIGHT
}

data class Node (
    val label: String,
    val leftLabel: String,
    val rightLabel: String
) {
    lateinit var left: Node
    lateinit var right: Node
}

data class Topology (
    val path: String,
    val nodes: Map<String, Node>
) {
    val turns: Iterator<Direction>
        get () {
            return object: Iterator<Direction> {
                private var i = 0
                override fun hasNext(): Boolean = true
                override fun next(): Direction {
                    return when (path[i++ % path.length]) {
                        'L' -> Direction.LEFT
                        'R' -> Direction.RIGHT
                        else -> throw Exception ()
                    }
                }
            }
        }
}

object Parser {
    private val pathPattern = Pattern.compile ("^[LR]+$")
    private val nodePattern = Pattern.compile ("^(\\w+) = \\((.+)\\)$")

    fun parse (rows: List<String>): Topology {
        val iter = rows.iterator ()
        val path = iter.next ()
        assert (pathPattern.matcher(path).matches ())

        val blank = iter.next ()
        assert (blank.isEmpty ())

        val nodes = mutableMapOf<String, Node> ()
        while (iter.hasNext ()) {
            val row = iter.next ()
            val matcher = nodePattern.matcher (row)
            assert (matcher.matches ())
            val label = matcher.group (1)
            val directions = matcher.group (2)
            val (left, right) = directions.split (", ")
            nodes[label] = Node (label, left, right)
        }

        for ((label, node) in nodes) {
            node.left = nodes[node.leftLabel] !!
            node.right = nodes[node.rightLabel] !!
        }
        return Topology (path, nodes)
    }
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    Parser.parse (inputs.sample1).apply {
        println (this)
        val iter = this.turns
        for (i in 0 .. 6) {
            println ("  ${iter.next ()}")
        }
    }
    return
}

// EOF