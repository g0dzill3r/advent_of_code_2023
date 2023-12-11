package aoc2023.week1.day2

import aoc2023.util.AOC
import java.util.regex.Pattern

/**
 * Parser for the input files.
 */

object Parser {
    fun parse (input: List<String>): List<Game> {
        return mutableListOf<Game>().apply {
            input.forEach {
                add (parse (it))
            }
        }
    }

    fun parse (row: String): Game {
        val match = prefix.matcher (row)
        assert (match.matches())
        val id = match.group(1).toInt ()
        val picks = parsePicks (match.group(2))
        return Game (id, picks)
    }

    fun parsePicks (str: String): List<Pick> {
        val els = str.split (";")
        return els.map { parsePick (it.trim ()) }
    }

    fun parsePick (str: String): Pick {
        val els = str.split (", ")
        var red = 0
        var green = 0
        var blue = 0
        els.forEach {
            val (numstr, color) = it.split (' ')
            val num = numstr.toInt ()
            when (color) {
                "red" -> red += num
                "green" -> green += num
                "blue" -> blue += num
                else -> throw Exception ("Unrecognized color: $color")
            }
        }
        return Pick (red, green, blue)
    }

    private val prefix = Pattern.compile ("^Game ([0-9]+): (.+)$")
}

enum class Color {
    RED, GREEN, BLUE;

    companion object {
        fun parse (str: String): Color = Color.valueOf (str.uppercase ())
    }
}

data class Game (
    val id: Int,
    val picks: List<Pick>
) {
    fun possible (limit: Limit): Boolean {
        for (pick in picks) {
            if (! pick.possible (limit)) {
                return false
            }
        }
        return true
    }

    val power: Int
        get () {
            val max = max
            var tmp = 1
            if (max.red > 0) {
                tmp *= max.red
            }
            if (max.green > 0) {
                tmp *= max.green
            }
            if (max.blue > 0) {
                tmp *= max.blue
            }
            return tmp
        }

    val max: Pick
        get () {
            var tmp = Pick (0, 0, 0)
            picks.forEach {
                tmp = tmp.max (it)
            }
            return tmp
        }
}

data class Pick (
    val red: Int,
    val green: Int,
    val blue: Int
) {
    fun max (pick: Pick): Pick {
        return Pick (
            Math.max (red, pick.red),
            Math.max (green, pick.green),
            Math.max (blue, pick.blue)
        )
    }

    fun possible (limit: Limit): Boolean {
        return red <= limit.red && green <= limit.green && blue <= limit.blue
    }
}

fun main (args: Array<String>) {
    val (s1, s2, p) = AOC.getInputs (2)
    val parsed = Parser.parse (s1)
    for (el in parsed) {
        println ("${el.id} - ${el.picks}")
        println (" - ${el.max}")
        println (" - ${el.power}")
    }
    return
}

// EOF