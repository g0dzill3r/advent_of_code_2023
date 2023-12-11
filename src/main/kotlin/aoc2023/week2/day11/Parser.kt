package aoc2023.week2.day11

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

enum class Tile (val symbol: Char, val drawn: Char) {
    GALAXY ('#', '■'),
    SPACE ('.', '•');

    companion object {
        private val symbols = Tile.values ().map { it.symbol to it }.toMap ()
        fun fromSymbol (symbol: Char): Tile = symbols[symbol] !!
    }
}

data class Galaxies (
    val grid: Grid<Tile>
) {
    fun dump () {
        grid.dump {
            "${it.drawn}"
        }
        println ("${grid.height}h x ${grid.width}w")
    }

    val pairs: List<Pair<Coordinate2, Coordinate2>>
        get () {
            val els = all
            return mutableListOf<Pair<Coordinate2, Coordinate2>> ().apply {
                val len = els.size
                for (i in 0 ..< len - 1) {
                    for (j in i + 1 ..< len) {
                        add (els[i] to els[j])
                    }
                }
            }
        }

    val all: List<Coordinate2>
        get () {
            val els = mutableListOf<Coordinate2> ()
            grid.scan { row, col, t ->
                if (t == Tile.GALAXY) {
                    els.add (Coordinate2 (row, col))
                }
            }
            return els
        }
}

object Parser {
    fun parse (rows: List<String>): Galaxies {
        val width = rows[0].length
        val height = rows.size
        val data = mutableListOf<Tile> ()

        rows.forEach {
            it.forEach {
                data.add (Tile.fromSymbol (it))
            }
        }

        return Galaxies (Grid (width, height, data))
    }
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(DAY)
    val galaxies = Parser.parse (inputs.sample1)
    galaxies.grid.dump {
        "${it.drawn}"
    }
    val (rows, cols) = galaxies.grid.empty()
    println ("empty rows: $rows")
    println ("empty cols: $cols")
    return
}

// EOF