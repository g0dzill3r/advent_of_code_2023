package aoc2023.week2.day13

import aoc2023.util.AOC
import aoc2023.util.peekable

enum class Material (val symbol: Char, val drawn: Char) {
    ASH ('.', '■'),
    ROCK ('#', '•');

    val inverse: Material
        get () = if (this == ASH) ROCK else ASH

    companion object {
        private val symbols = entries.associateBy { it.symbol }
        fun fromSymbol (symbol: Char): Material = symbols [symbol] !!
    }
}

data class Patterns (
    val grids: List<Grid>
)

object Parser {
    fun parse (rows: List<String>): Patterns {
        val iter = rows.iterator ().peekable ()
        val grids = mutableListOf<Grid> ()
        var materials = mutableListOf<Material> ()
        var width = -1
        var height = -1

        fun flush () {
            grids.add (Grid (width, height, materials))
            height = -1
            width = -1
            materials = mutableListOf<Material> ()
        }

        while (iter.hasNext ()) {
            val row = iter.next ()
            if (row.isEmpty ()) {
                flush ()
            } else {
                if (materials.isEmpty ()) {
                    width = row.length
                    height = 0
                }
                row.forEach {
                    materials.add (Material.fromSymbol (it))
                }
                height ++
            }
        }

        flush ()
        return Patterns (grids)
    }
}

fun main () {
    val inputs = AOC.getInputs (DAY)
    val patterns = Parser.parse (inputs.sample2)
    patterns.grids.forEach {
        it.dump ()
        println ("${it.height}h x ${it.width}w")
        println ("HORIZONTAL: ${it.horizontalSeam}")
        println ("VERTICAL: ${it.verticalSeam}")
        println ()
    }

    val grid = patterns.grids[0]
    for (i in 0 ..< grid.height) {
        println ("ROW $i: ${grid.row (i)}")
    }
    println ()
    for (i in 0 ..< grid.width) {
        println ("COL $i: ${grid.column (i)}")
    }


    return
}

// EOF