package aoc2023.week3.day15

import aoc2023.util.AOC

enum class Op {
    DASH,
    EQUALS
}

data class Entry (
    val symbol: String,
    val operation: Op,
    val value: Int = -1
) {
    override fun toString (): String {
        return when (operation) {
            Op.DASH -> "${symbol}-"
            Op.EQUALS -> "${symbol}=${value}"
        }
    }

    val slot: Int = hash (symbol)

    companion object {
        fun parse (str: String): Entry {
            val i = str.indexOf ("=")
            return if (i != -1) {
                Entry (str.substring (0, i), Op.EQUALS, str.substring (i + 1, str.length).toInt ())
            } else if (str.endsWith("-")) {
                Entry (str.substring (0, str.length - 1), Op.DASH)
            } else {
                throw IllegalStateException ("Invalid entry: $str")
            }
        }
    }
}

object Parser {
    fun parse (rows: List<String>): List<Entry> {
        if (rows.size != 1) {
            throw IllegalStateException ("Too many rows.")
        }
        val split = rows[0].split (",")
        return split.map { Entry.parse (it) }
    }
}

fun hash (str: String): Int {
    var current = 0
    str.forEach {
        current += it.code
        current *= 17
        current %= 256
    }
    return current
}


// EOF