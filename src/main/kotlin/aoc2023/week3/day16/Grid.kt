package aoc2023.week3.day16

import aoc2023.week1.day3.Coordinate2

data class Grid  (
    val width: Int,
    val height: Int,
    val data: MutableList<Type>,
) {
    val energized = data.map { false }.toMutableList()
    fun energize (coord: Coordinate2) {
        energized[toIndex (coord)] = true
    }
    val energizedCount: Int
        get () = energized.filter { it }.count ()

    fun isValid (coord: Coordinate2): Boolean {
        return coord.row in 0 ..< height && coord.col in 0 ..< width
    }
    fun toIndex (coord: Coordinate2): Int = toIndex (coord.row, coord.col)
    fun toIndex (row: Int, col: Int): Int = row * width + col
    fun fromIndex (index: Int): Coordinate2 = Coordinate2 (index / width, index % width)

    fun getTile (row: Int, col: Int) = data[toIndex (row, col)]
    fun getTile (coord: Coordinate2) = getTile (coord.row, coord.col)
    fun setTile (index: Int, t: Type) = setTile (fromIndex (index), t)
    fun setTile (coord: Coordinate2, t: Type) = setTile (coord.row, coord.col, t)
    fun setTile (row: Int, col: Int, t: Type) {
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
            "${it.symbol}"
        }
        return
    }

    fun dump (render: (t: Type) -> String) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                print (render (data[toIndex (row, col)]))
            }
            println ()
        }
        return
    }

    fun dumpEnergized () {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                val energized = energized [toIndex (row, col)]
                val drawn = if (energized) '#' else '.'
                print (drawn)
            }
            println ()
        }
        return
    }

    var beams = mutableListOf<Beam> ().apply {
        add (Beam (Direction.RIGHT, Coordinate2 (0, -1)))
    }.toList ()

    val already = data.map {
        mutableMapOf<Direction, Boolean> ()
    }

    fun bounce () {
        var tick = 0

        while (beams.isNotEmpty ()) {
            val newBeams = mutableListOf<Beam> ()
            beams.forEach { beam ->
                if (isValid (beam.coord)) {
                    already[toIndex (beam.coord)][beam.direction] = true
                }
                newBeams.addAll (beam.update (this))
            }

            beams = newBeams
                .filter {
                    isValid (it.coord)
                }
                .filter {
                    already[toIndex (it.coord)][it.direction] == null
                }
            tick ++
        }
        return
    }
}

// EOF