package aoc2023.week2.day10

import aoc2023.util.AOC
import aoc2023.util.repeat
import aoc2023.week1.day3.Coordinate2

val DAY = 10

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

enum class TileType (val symbol: Char, val drawn: Char, val north: Boolean, val east: Boolean, val south: Boolean, val west: Boolean) {
    NS_PIPE ('|', '┃', true, false, true, false),
    EW_PIPE ('-', '━', false, true, false, true),
    NE_PIPE ('L', '┗', true, true, false, false),
    NW_PIPE ('J', '┛', true, false, false, true),
    SW_PIPE ('7', '┓', false, false, true, true),
    SE_PIPE ('F', '┏', false, true, true, false),
    GROUND ('.','.', false, false, false, false),
    START ('S', 'S', false, false, false, false);

    companion object {
        private val symbols = mutableMapOf<Char, TileType> ().apply {
            TileType.values ().forEach {
                this[it.symbol] = it
            }
        }

        fun fromSymbol (symbol: Char): TileType = symbols[symbol] ?: throw Exception ("Invalid TileType: $symbol")
    }
}

data class Tile (
    val type: TileType
)

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

data class Map (
    var _grid: Grid<Tile>
) {
    lateinit var grid: Grid<Tile>
    lateinit var start: Coordinate2

    init {
        grid = expand (_grid)
        fixStart ()
        maskGrid ()
    }

    /**
     * Removes all the non-path pipes so that they're no longer a consideration.
     */

    private fun maskGrid () {
        val path = pipePath
        for (row in 0 ..< path.height) {
            for (col in 0 ..< path.width) {
                if (! path.getTile (row, col)) {
                    grid.setTile (row, col, Tile (TileType.GROUND))
                }
            }
        }
        return
    }

    /**
     * Add a border around the whole data structure so that we know for certain that we
     * have tiles that are fully outside the path.
     */

    private fun expand (grid: Grid<Tile>): Grid<Tile> {
        val tmp = mutableListOf<Tile> ()
        val newWidth = grid.width + 2
        val newHeight = grid.height + 2
        newWidth.repeat {
            tmp.add (Tile (TileType.GROUND))
        }
        for (row in 0 ..< grid.height) {
            tmp.add (Tile (TileType.GROUND))
            for (col in 0 ..< grid.width) {
                tmp.add (grid.getTile (row, col))
            }
            tmp.add (Tile (TileType.GROUND))
        }
        newWidth.repeat {
            tmp.add (Tile (TileType.GROUND))
        }
        return Grid<Tile> (newWidth, newHeight, tmp)
    }

    /**
     * Print out a visual representation of the grid.
     */

    fun dump () {
        for (row in 0..< grid.height) {
            for (col in 0..< grid.width) {
                val tile = grid.getTile (row, col)
                print (tile.type.drawn)
//                print (' ')
            }
            println ()
        }
        println ("START $start")
    }

    /**
     * Replace the start tile with the proper pipe tile by infering the
     * proper tile types.
     */

    private fun fixStart () {
        val startIndex = grid.data.indexOfFirst { it.type == TileType.START }
        start = Coordinate2 (startIndex / grid.width, startIndex % grid.width)

        val north = grid.getTile (start.delta (Direction.NORTH.delta)).type.south
        val south = grid.getTile (start.delta (Direction.SOUTH.delta)).type.north
        val east = grid.getTile (start.delta (Direction.EAST.delta)).type.west
        val west = grid.getTile (start.delta (Direction.WEST.delta)).type.east

        val tileType = when {
            north && south -> TileType.NS_PIPE
            north && east -> TileType.NE_PIPE
            north && west -> TileType.NW_PIPE
            east && west -> TileType.EW_PIPE
            south && east -> TileType.SE_PIPE
            south && west -> TileType.SW_PIPE
            else -> throw Exception ("Cannot infer tileType.")
        }

        grid.setTile (grid.fromIndex (startIndex), Tile (tileType))
        return
    }

    val distances: Grid<Int>
        get () = calculateDistances ()

    /**
     * Used to calculate the distance from the starting point to all the traversable
     * tiles in the grid.
     */

    private fun calculateDistances (): Grid<Int> {
        val EMPTY = -1

        // Create the initial grid

        val data = mutableListOf<Int> ().apply {
            (grid.width * grid.height).repeat {
                add (EMPTY)
            }
        }
        val distances = Grid (grid.width, grid.height, data)

        // And then walk the grid until we've exhausted all options

        var walkers = mutableListOf<Coordinate2> ().apply {
            add (start)
        }
        var steps = 0
        distances.setTile (start, steps)

        while (walkers.isNotEmpty()) {
            steps ++
            val tmp = mutableListOf<Coordinate2> ()
            walkers.forEach {
                val type = grid.getTile (it).type
                if (type.north && distances.getTile (it.delta (Direction.NORTH.delta)) == EMPTY) {
                    tmp.add (it.delta (Direction.NORTH.delta))
                }
                if (type.south && distances.getTile (it.delta (Direction.SOUTH.delta)) == EMPTY) {
                    tmp.add (it.delta (Direction.SOUTH.delta))
                }
                if (type.east && distances.getTile (it.delta (Direction.EAST.delta)) == EMPTY) {
                    tmp.add (it.delta (Direction.EAST.delta))
                }
                if (type.west && distances.getTile (it.delta (Direction.WEST.delta)) == EMPTY) {
                    tmp.add (it.delta (Direction.WEST.delta))
                }
            }
            tmp.forEach {
                distances.setTile (it, steps)
            }
            walkers = tmp
        }

        return distances
    }

    val pipePath: Grid<Boolean>
        get () = calculatePipePath ()

    /**
     * Calculate the tiles that are occupied by the actual pipe path.
     */

    private fun calculatePipePath (): Grid<Boolean> {

        // Create a grid of booleans for us to use to represent the pipe path

        val data = mutableListOf<Boolean>().apply {
            (grid.width * grid.height).repeat {
                add(false)
            }
        }
        val path = Grid<Boolean> (grid.width, grid.height, data)

        // Walk the pipe and mark the tiles that it occupies

        var coords = listOf<Coordinate2> (start)
        while (true) {
            val tmp = mutableListOf<Coordinate2> ()
            coords.forEach {
                path.setTile (it, true)
                val type = grid.getTile (it).type
                if (type.north) {
                    tmp.add (it.delta (Direction.NORTH.delta))
                }
                if (type.south) {
                    tmp.add (it.delta (Direction.SOUTH.delta))
                }
                if (type.east) {
                    tmp.add (it.delta (Direction.EAST.delta))
                }
                if (type.west) {
                    tmp.add (it.delta (Direction.WEST.delta))
                }
            }
            coords = tmp.filter {
                ! path.getTile (it)
            }
            if (coords.isEmpty()) {
                break
            }
        }

        return Grid(grid.width, grid.height, data)
    }


    val enclosed: Grid<Nestable>
        get () = calculateEnclosed ()

    /**
     *
     */

    private fun calculateEnclosed (): Grid<Nestable> {
        // Create the initial grid

        val data = mutableListOf<Nestable> ().apply {
            (grid.width * grid.height).repeat { index ->
                val tile = grid.getTile (grid.fromIndex (index))
                val nestable = when (tile.type) {
                    TileType.GROUND -> Nestable.UNKNOWN
                    else -> Nestable.PIPE
                }
                add (nestable)
            }
        }
        val enclosed = Grid (grid.width, grid.height, data)

        // Use a flood fill approach to detecting the reachable squares from the outside; we'll
        // conclude that these are exterior to the loop and are therefore not nestable,

        val start = Coordinate2 (0, 0)
        var active = mutableListOf<Coordinate2> ().apply {
            add (start)
        }

        while (true) {
            val tmp = mutableListOf<Coordinate2> ()
            active.forEach { coord ->
                enclosed.setTile (coord, Nestable.NO)
                Direction.values ().forEach { direction ->
                    val next = coord.delta (direction.delta)
                    if (grid.contains (next) && enclosed.getTile (next) == Nestable.UNKNOWN) {
                        if (! tmp.contains (next)) {
                            tmp.add (next)
                        }
                    }
                }
            }

            active = tmp
            if (active.isEmpty()) {
                break
            }
        }

        // Now we need to look at all of the remaining unknown tiles as possible nestable locations.
        // We'll have to determine if it's inside or outside.

        enclosed.data.forEachIndexed { index, nestable ->
            if (nestable == Nestable.UNKNOWN) {
                if (isInside (index)) {
                    enclosed.setTile(index, Nestable.YES)
                } else {
                    enclosed.setTile(index, Nestable.NO)
                }
            }
        }

        return enclosed
    }

    /**
     * Determines whether a specific tile lies inside or outside of the piped area.
     */

    private fun isInside (index: Int): Boolean {
        val coord = grid.fromIndex (index)

        // Get the set of tiles leading up to the tile

        val start = grid.toIndex (Coordinate2 (coord.row, 0))
        val end = grid.toIndex (Coordinate2 (coord.row, coord.col))
        val path = grid.data.subList (start, end).map { it.type }.filter { it != TileType.EW_PIPE }

        // Calculate the number of boundaries we cross

        var crossed = 0
        val iter = path.iterator ()
        while (iter.hasNext ()) {
            when (iter.next ()) {
                TileType.GROUND -> Unit
                TileType.NS_PIPE -> crossed ++
                TileType.NE_PIPE -> {
                    if (iter.next ().south) {
                        crossed ++
                    }
                }
                TileType.SE_PIPE -> {
                    if (iter.next ().north) {
                        crossed ++
                    }
                }
                TileType.START -> throw IllegalStateException ()
                TileType.NW_PIPE -> throw IllegalStateException ()
                TileType.SW_PIPE -> throw IllegalStateException ()
                TileType.EW_PIPE -> throw IllegalStateException ()
            }
        }

        return crossed % 2 == 1
    }
}

enum class Nestable {
    YES,
    NO,
    UNKNOWN,
    PIPE
}

object Parser {
    fun parse (rows: List<String>): Map {
        val width = rows[0].length
        val height = rows.size
        val tiles = mutableListOf<Tile> ()

        rows.forEach {
            it.forEach {
                val tileType = TileType.fromSymbol (it)
                tiles.add (Tile (tileType))
            }
        }

        val grid = Grid<Tile> (width, height, tiles)
        return Map (grid)
    }
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    val map = Parser.parse (inputs.sample2)
    map.dump ()

    val path = map.pipePath
    path.dump {
        if (it) {
            "■"

        } else {
            "•"
        }
    }

    val distances = map.distances
    distances.dump ()
    println (distances.data.maxOf { it })
    return
}

// EOF