package aoc2023.week2.day10

import aoc2023.week1.day3.Coordinate2

enum class Direction (val delta: Coordinate2) {
    NORTH (Coordinate2 (-1, 0)),
    SOUTH (Coordinate2 (1, 0)),
    EAST (Coordinate2 (0, 1)),
    WEST (Coordinate2 (0, -1));

    val opposing: Direction
        get () {
            return when (this){
                Direction.NORTH -> Direction.SOUTH
                Direction.SOUTH -> Direction.NORTH
                Direction.EAST -> Direction.WEST
                Direction.WEST -> Direction.EAST
            }
        }
}

// EOF