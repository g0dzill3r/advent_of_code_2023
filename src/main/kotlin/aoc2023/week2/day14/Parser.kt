package aoc2023.week2.day14

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

enum class Rock (val symbol: Char) {
    NONE ('.'),
    ROUNDED ('O'),
    CUBE ('#');

    companion object {
        private val symbols = values ().associateBy { it.symbol }.toMap ()
        fun fromSymbol (symbol: Char): Rock = symbols [symbol] !!
    }
}

enum class Direction (val delta: Coordinate2) {
    NORTH (Coordinate2 (-1, 0)),
    SOUTH (Coordinate2 (1, 0)),
    EAST (Coordinate2 (0, 1)),
    WEST (Coordinate2 (0, -1))
}

object Parser {
    fun parse (rows: List<String>): Grid {
        val width = rows[0].length
        val height = rows.size
        val rocks = mutableListOf<Rock> ()
        rows.forEach { row ->
            row.forEach {
                rocks.add (Rock.fromSymbol (it))
            }
        }
        return Grid (width, height, rocks)
    }
}

fun main () {
    val inputs = AOC.getInputs (DAY)
    val grid = Parser.parse (inputs.sample1)
    grid.dump ()

    println ("=")
    grid.roll (Coordinate2 (3, 0), Direction.NORTH.delta)
    grid.dump ()

    return
}

// EOF