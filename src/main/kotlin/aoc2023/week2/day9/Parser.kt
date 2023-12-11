package aoc2023.week2.day9

import aoc2023.util.AOC
import java.util.regex.Pattern

val DAY = 9

data class Data (
    val rows: List<Measurements>
) {
    override fun toString (): String {
        return rows.map { it.measurement }.dump ()
    }
}

fun List<List<Int>>.dump (): String {
    val width =maxOf { it.maxOf { it.toString ().length } }
    val buf = StringBuffer ()
    this.forEach {
        it.forEachIndexed { i, value ->
            if (i != 0) {
                buf.append (' ')
            }
            buf.append (String.format ("%${width}d", value))
        }
        buf.append ("\n")
    }
    return buf.toString()
}

data class Measurements (
    val measurement: List<Int>
) {
    val reduced: List<MutableList<Int>>
        get () {
            val tmp = mutableListOf<MutableList<Int>> ()
            tmp.add (mutableListOf<Int> ().apply {
                addAll (measurement)
            })
            while (! tmp.last().all { it == 0 }) {
                val deltas = mutableListOf<Int> ()
                val last = tmp.last ()
                for (i in 1 until last.size) {
                    deltas.add (last[i] - last[i-1])
                }
                tmp.add (deltas)
            }
            return tmp
        }

    val extrapolate: Int
        get () {
            val tmp = reduced
            for (i in tmp.size - 1 downTo 0) {
                if (i == tmp.size - 1) {
                    tmp[i].add (0)
                } else {
                    tmp[i].add (tmp[i + 1].last () + tmp[i].last ())
                }
            }
            return tmp[0].last ()
        }

    val reverseExtrapolate: Int
        get () {
            val tmp = reduced
            for (i in tmp.size - 1 downTo 0) {
                if (i == tmp.size - 1) {
                    tmp[i].add (0, 0)
                } else {
                    tmp[i].add (0, tmp[i].first () - tmp[i + 1].first ())
                }
            }
            println (tmp.dump ())
            return tmp[0].first()
        }
}

object Parser {
    fun parse (rows: List<String>): Data {
        val data = rows.map {
            Measurements (it.trim ().split (Pattern.compile ("\\s+")).map { it.toInt () })
        }
        return Data (data)
    }
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs(DAY)
    val parsed = Parser.parse (inputs.sample1)
    println (parsed)

    parsed.rows.forEach {
        println ("\n/$it/")
//        println (it.extrapolate)
        println (it.measurement)
        println (it.reverseExtrapolate)
    }
    return
}

// EOF