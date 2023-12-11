package aoc2023.week2.day11

import aoc2023.util.repeat
import aoc2023.week1.day3.Coordinate2

data class Grid<T>  (
    var width: Int,
    var height: Int,
    val data: MutableList<T>,
) {
    fun toIndex (coord: Coordinate2): Int = toIndex (coord.row, coord.col)
    fun toIndex (row: Int, col: Int): Int = row * width + col
    fun fromIndex (index: Int): Coordinate2 = Coordinate2 (index / width, index % width)

    fun getTile (row: Int, col: Int) = data[toIndex (row, col)]
    fun getTile (coord: Coordinate2) = getTile (coord.row, coord.col)
    fun setTile (index: Int, t: T) = setTile (fromIndex (index), t)
    fun setTile (coord: Coordinate2, t: T) = setTile (coord.row, coord.col, t)
    fun setTile (row: Int, col: Int, t: T) {
        data[toIndex (row, col)] = t
    }

    fun contains (coord: Coordinate2): Boolean {
        return if (coord.row < 0 || coord.col < 0 || coord.row >= height || coord.col >= width) {
            false
        } else {
            true
        }
    }

    fun dump () {
        val size = data.maxOf {
            it.toString ().length
        }
        dump {
            String.format ("%${size}s ", it)
        }
        return
    }

    fun dump (render: (t: T) -> String) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                print (render (data[toIndex (row, col)]))
            }
            println ()
        }
        return
    }

    /**
     * An iterator across all the tiles.
     */

    fun scan (func: (row: Int, col: Int, t: T) -> Unit) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                func (row, col, getTile (row, col))
            }
        }
    }

    val galaxies: List<Coordinate2>
        get () {
            return mutableListOf<Coordinate2> ().apply {
                scan { row, col, tile ->
                    if (tile == Tile.GALAXY) {
                        add (Coordinate2 (row, col))
                    }
                }
            }
        }

    /**
     * Returns the empty rows and columns.
     */

    fun empty (): Pair<List<Int>, List<Int>> {
        val rows = (0 ..< height).toMutableList()
        val cols = (0 ..< width).toMutableList ()
        galaxies.forEach {
            rows.remove (it.row)
            cols.remove (it.col)
        }
        return rows to cols
    }

    /**
     * Performs one expansion cycle by doubling any empty rows and columns.
     */

    fun expand (t: T) {
        val (rows, cols) = empty ()
        rows.forEachIndexed { index, i ->
            expandRow (i + index, t)
        }
        cols.forEachIndexed { index, i ->
            expandColumn (i + index, t)
        }
        return
    }

    private fun expandRow (row: Int, t: T) {
        val coord = toIndex (row, 0)
        width.repeat {
            data.add (coord, t)
        }
        height ++
        return
    }

    private fun expandColumn (col: Int, t: T) {
        for (j in 0 ..< height) {
            val row = height - 1 - j
            val coord = toIndex (row, col)
            data.add (coord, t)
        }
        width ++
        return
    }
}

// EOF