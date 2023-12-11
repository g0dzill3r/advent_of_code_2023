package aoc2023.week1.day3

import aoc2023.util.AOC

val DAY = 3

object Parser {
    fun parse (rows: List<String>): Schematic = Schematic (rows)
}

data class Schematic (
    val rows: List<String>,
    val width: Int = rows[0].length,
    val height: Int = rows.size
) {
    fun isValid (row: Int, col: Int): Boolean = row in 0 ..< height && col in 0 ..< width

    fun surrounding (part: PartNumber): List<Coordinate2> {
        val list = mutableListOf<Coordinate2> ()
        list.add (Coordinate2 (part.coord.row, part.coord.col - 1))
        list.add (Coordinate2 (part.coord.row, part.coord.col + part.digits))
        for (col in -1 .. part.digits) {
            list.add (Coordinate2 (part.coord.row - 1, part.coord.col + col))
            list.add (Coordinate2 (part.coord.row + 1, part.coord.col + col))
        }
        return list.filter { isValid (it.row, it.col) }
    }

    fun iterate (func: (Coordinate2, Element) -> Unit) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                func (Coordinate2 (row, col), get (row, col))
            }
        }
    }

    fun get (row: Int, col: Int): Element {
        val c = rows[row][col]
        return if (c.isDigit()) {
            Element.NUMBER ("$c".toInt ())
        } else if (c == '.') {
            Element.EMPTY ()
        } else {
            Element.SYMBOL (c)
        }
    }

    val numbers: List<PartNumber>
        get () {
            val list = mutableListOf<PartNumber> ()
            for (row in 0..< height) {
                var accum = false
                var total = 0
                var start: Coordinate2? = null
                var save = {
                    list.add (PartNumber (total, start !!))
                    total = 0
                    start = null
                    accum = false
                }
                for (col in 0..< width) {
                    val el = get (row, col)
                    if (accum) {
                        when (el) {
                            is Element.EMPTY, is Element.SYMBOL -> save ()
                            is Element.NUMBER -> {
                                total = total * 10 + el.value
                            }
                        }
                    } else {
                        when (el) {
                            is Element.EMPTY -> Unit
                            is Element.SYMBOL -> Unit
                            is Element.NUMBER -> {
                                accum = true
                                total = el.value
                                start = Coordinate2 (row, col)
                            }
                        }
                    }
                }
                if (accum) {
                    save ()
                }
            }
            return list
        }

    val symbols: List<Coordinate2>
        get () {
            return mutableListOf<Coordinate2>().apply {
                iterate { coord, el ->
                    if (el is Element.SYMBOL) {
                        add (coord)
                    }
                }
            }
        }

    fun isAdjacent (part: PartNumber, coord: Coordinate2): Boolean {
        return surrounding (part).contains (coord)
    }
}

data class PartNumber (
    val value: Int,
    val coord: Coordinate2,
    val digits: Int = "$value".length
)

data class Coordinate2 (
    val row: Int,
    val col: Int
) {
    fun delta (delta: Coordinate2): Coordinate2 = Coordinate2 (row + delta.row, col + delta.col)

    override fun toString(): String = "($row, $col)"
}

sealed class Element {
    class EMPTY : Element ()
    data class SYMBOL (val symbol: Char) : Element ()
    data class NUMBER (val value: Int) : Element ()
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    val schematic = Parser.parse (inputs.sample1)
    println (schematic)

    val symbols = schematic.symbols
    println ("symbols")
    symbols.forEach {
        println(" - $it")
    }

//    val numbers = schematic.numbers
//    println (numbers)
//    println (numbers[2])
//    println (schematic.surrounding (numbers [2]))
    return
}

// eOF
