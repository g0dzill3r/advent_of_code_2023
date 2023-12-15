package aoc2023.week2.day13

import aoc2023.week1.day3.Coordinate2

data class Grid (
    val width: Int,
    val height: Int,
    val data: MutableList<Material>,
) {
    var override: Coordinate2? = null

    fun toIndex (coord: Coordinate2): Int = toIndex (coord.row, coord.col)
    fun toIndex (row: Int, col: Int): Int = row * width + col
    fun fromIndex (index: Int): Coordinate2 = Coordinate2 (index / width, index % width)

    fun getTile (row: Int, col: Int): Material {
        val result = data[toIndex (row, col)]
        if (override != null) {
            if (row == override!!.row && col == override!!.col) {
                return result.inverse
            }
        }
        return result
    }
    fun getTile (coord: Coordinate2) = getTile (coord.row, coord.col)
    fun setTile (index: Int, t: Material) = setTile (fromIndex (index), t)
    fun setTile (coord: Coordinate2, t: Material) = setTile (coord.row, coord.col, t)
    fun setTile (row: Int, col: Int, t: Material) {
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

        dump { it: Material ->
            "${it.symbol}"
        }
        return
    }

    fun dump (render: (t: Material) -> String) {
        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                print (render (getTile (row, col)))
            }
            println ()
        }
        return
    }

    fun column (col: Int): String {
        return StringBuffer ().apply {
            for (row in 0 ..< height) {
                append (getTile (row, col).symbol)
            }
        }.toString ()
    }

    fun row (row: Int): String {
        return StringBuffer ().apply {
            for (col in 0 ..< width) {
                append (getTile (row, col).symbol)
            }
        }.toString ()
    }

    fun splitHorizontal (col: Int): Pair<String, String> {
        val left = StringBuffer ().apply {
            for (i in col downTo 0) {
                append (column (i))
            }
        }.toString ()
        val right = StringBuffer ().apply {
            for (i in col + 1 ..< width) {
                append (column (i))
            }
        }.toString ()
        return left to right
    }

    fun splitVertical (row: Int): Pair<String, String> {
        val top = StringBuffer ().apply {
            for (i in row downTo 0) {
                append (row (i))
            }
        }.toString ()
        val bottom = StringBuffer ().apply {
            for (i in row + 1 ..< height) {
                append (row (i))
            }
        }.toString ()
        return top to bottom
    }

    val seams: Pair<Int?, Int?>
        get () = horizontalSeam to verticalSeam

    val hasSeam: Boolean
        get () {
            val (horizontal, vertical) = seams
            return horizontal != null || vertical != null
        }

    val verticalSeam: Int?
        get () {
            for (col in 0 ..< width - 1) {
                val (left, right) = splitHorizontal (col)
                if (matches (left, right)) {
                    return col
                }
            }
            return null
        }

    val horizontalSeam: Int?
        get () {
            for (row in 0 ..< height  - 1) {
                val (top, bottom) = splitVertical (row)
                if (matches (top, bottom)) {
                    return row
                }
            }
            return null
        }

    private fun matches (s1: String, s2: String): Boolean {
        return when {
            s1.length > s2.length -> s1.startsWith (s2)
            else -> s2.startsWith (s1)
        }
    }

    val possibilities: Coordinate2
        get () {
            try {
                val possible = mutableMapOf<Coordinate2?, Pair<Int?, Int?>> ()

                // Remember what the original seams were so that we only trigger
                // on an actual change

                override = null
//                possible.add (null to seams)

                for (row in 0 ..< height) {
                    for (col in 0 ..< width) {
                        override = Coordinate2 (row, col)
                        val curr = seams
                        if (curr.first != null || curr.second != null) {
                            if (! possible.contains (override !!))
                                return Coordinate2(row, col)
                            }
                        }
                    }
                throw Exception ("No alternate.")
            }
            finally {
                override = null
            }

            // NOT REACHED
        }
}

// EOF