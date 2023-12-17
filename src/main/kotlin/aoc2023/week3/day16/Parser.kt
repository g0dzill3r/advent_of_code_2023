package aoc2023.week3.day16

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

/**
 * Captures the state of a light bean as it meanders about the grid
 * and interacts with the mirrors.
 */

data class Beam (val direction: Direction, val coord: Coordinate2) {
    val updated: Coordinate2
        get () = coord.delta (direction.delta)

    fun update (grid: Grid): List<Beam> {
        val curr = updated
        val updates = mutableListOf<Beam> ()
        if (grid.isValid (curr)) {
            grid.energize (curr)
            val type = grid.getTile(curr)
            when (type) {
                Type.EMPTY -> {
                    updates.add (Beam (direction, curr))
                }
                Type.LEFT_MIRROR -> {
                    val newDirection = when (direction) {
                        Direction.UP -> Direction.LEFT
                        Direction.DOWN -> Direction.RIGHT
                        Direction.LEFT -> Direction.UP
                        Direction.RIGHT -> Direction.DOWN
                    }
                    updates.add (Beam (newDirection, curr))
                }
                Type.RIGHT_MIRROR -> {
                    val newDirection = when (direction) {
                        Direction.UP -> Direction.RIGHT
                        Direction.DOWN -> Direction.LEFT
                        Direction.LEFT -> Direction.DOWN
                        Direction.RIGHT -> Direction.UP
                    }
                    updates.add (Beam (newDirection, curr))
                }
                Type.UP_DOWN_SPLITTER -> {
                    when (direction) {
                        Direction.UP, Direction.DOWN -> {
                            updates.add (Beam (direction, curr))
                        }
                        Direction.LEFT, Direction.RIGHT -> {
                            updates.add (Beam (Direction.UP, curr))
                            updates.add (Beam (Direction.DOWN, curr))
                        }
                    }
                }
                Type.LEFT_RIGHT_SPLITTER -> {
                    when (direction) {
                        Direction.LEFT, Direction.RIGHT -> {
                            updates.add (Beam (direction, curr))
                        }
                        Direction.UP, Direction.DOWN -> {
                            updates.add (Beam (Direction.RIGHT, curr))
                            updates.add (Beam (Direction.LEFT, curr))
                        }
                    }
                }
            }
        }
        return updates
    }
}

enum class Direction (val delta: Coordinate2) {
    LEFT (Coordinate2 (0, -1)),
    RIGHT (Coordinate2 (0, 1)),
    UP (Coordinate2 (-1, 0)),
    DOWN (Coordinate2 (1, 0))
}

enum class Type (val symbol: Char) {
    EMPTY ('.'),
    RIGHT_MIRROR ('/'),
    LEFT_MIRROR ('\\'),
    UP_DOWN_SPLITTER ('|'),
    LEFT_RIGHT_SPLITTER ('-');

    companion object {
        private val symbols = values ().associateBy { it.symbol }.toMap ()
        fun fromSymbol (symbol: Char): Type = symbols[symbol]!!
    }
}

object Parser {
    fun parse (rows: List<String>): Grid {
        val width = rows[0].length
        val height = rows.size
        val types = mutableListOf<Type> ().apply {
            rows.forEach { row ->
                row.forEach {
                    add (Type.fromSymbol (it))
                }
            }
        }
        return Grid (width, height, types)
    }
}

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.sample1)
    grid.dump ()
    return
}

// EOF