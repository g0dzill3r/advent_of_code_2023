package aoc2023.week2.day14

import aoc2023.week1.day3.Coordinate2
import java.lang.IllegalStateException

data class Grid (
    val width: Int,
    val height: Int,
    val data: MutableList<Rock>,
) {
    fun isValid (coord: Coordinate2): Boolean {
        val (row, col) = coord
        return row in 0..< height && col in 0 ..< width
    }

    fun toIndex (coord: Coordinate2): Int = toIndex (coord.row, coord.col)
    fun toIndex (row: Int, col: Int): Int = row * width + col
    fun fromIndex (index: Int): Coordinate2 = Coordinate2 (index / width, index % width)

    fun getTile (row: Int, col: Int): Rock {
        val result = data[toIndex (row, col)]
        return result
    }
    fun getTile (coord: Coordinate2) = getTile (coord.row, coord.col)
    fun setTile (index: Int, t: Rock) = setTile (fromIndex (index), t)
    fun setTile (coord: Coordinate2, t: Rock) = setTile (coord.row, coord.col, t)
    fun setTile (row: Int, col: Int, t: Rock) {
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

        dump { it: Rock ->
            "${it.symbol}"
        }
        return
    }

    fun dump (render: (t: Rock) -> String) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                print (render (getTile (row, col)))
            }
            println ()
        }
        return
    }

    /**
     * Tile the platform in the specified direction and allow the round
     * rocks to roll as far as they're able to.
     */

    fun tilt (dir: Direction) {
        when (dir) {
            Direction.NORTH -> tilt (1 ..< height, 0 ..< width, dir.delta)
            Direction.SOUTH -> tilt ((height - 2) downTo 0, 0 ..< width, dir.delta)
            Direction.EAST -> tilt (0 ..< height, (height - 2) downTo 0, dir.delta)
            Direction.WEST -> tilt (0 ..< height, 1 ..< width, dir.delta)
        }
    }

    /**
     * Update the locations of the round rocks based on tilting of the platform.
     */

    private fun tilt (rows: IntProgression, cols: IntProgression, delta: Coordinate2) {
        for (row in rows) {
            for (col in cols) {
                val loc = Coordinate2 (row, col)
                if (getTile (loc) == Rock.ROUNDED) {
                    roll (loc, delta)
                }
            }
        }
    }

    /**
     * Rolls a rock in the specified direction as far as it will go.
     */

    fun roll (coord: Coordinate2, delta: Coordinate2) {
        if (getTile (coord) != Rock.ROUNDED) {
            throw IllegalStateException ("Not a rounded rock: $coord")
        }
        var updated = coord
        while (true) {
            val previous = updated
            updated = updated.delta (delta)
            if (! isValid (updated)) {
                return
            }
            if (getTile (updated) != Rock.NONE) {
                return
            }
            setTile (previous, Rock.NONE)
            setTile (updated, Rock.ROUNDED)
        }
    }

    fun map (func: (coord: Coordinate2) -> Unit) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                func (Coordinate2 (row, col))
            }
        }
        return
    }
}

// EOF