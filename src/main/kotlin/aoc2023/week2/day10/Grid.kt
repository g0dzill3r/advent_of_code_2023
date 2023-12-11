package aoc2023.week2.day10

import aoc2023.week1.day3.Coordinate2

data class Grid<T>  (
    val width: Int,
    val height: Int,
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
}

// EOF