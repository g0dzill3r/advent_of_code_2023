package aoc2023.week2.day12

import aoc2023.util.AOC

enum class Condition (val symbol: Char) {
    OPERATIONAL ('.'),
    DAMAGED ('#'),
    UNKNOWN ('?');

    companion object {
        private val symbols = Condition.values ().map { it.symbol to it }.toMap ()
        fun fromSymbol (symbol: Char): Condition = symbols[symbol] !!
        val known = listOf (Condition.OPERATIONAL, Condition.DAMAGED)
    }
}

data class Record (
    val conditions: List<Condition>,
    val contiguous: List<Int>
) {
    fun replaced (list: List<Condition>): Record {
        if (list.size != unknowns) {
            throw IllegalStateException ("Mismatched replacement count: ${list.size} != $unknowns")
        }
        val iter = list.iterator ()
        val updated = conditions.map {
            if (it == Condition.UNKNOWN) {
                iter.next ()
            } else {
                it
            }
        }
        return Record (updated, contiguous)
    }

    val unknowns: Int
        get () = conditions.filter { it == Condition.UNKNOWN }.count ()

    val encoded: String
        get () {
            return StringBuffer().apply {
                conditions.forEach {
                    append (it.symbol)
                }
            }.toString()
        }

    val groups: List<Int>
        get () = encoded.split (".").map { it.length }.filter { it != 0 }

    val isValid: Boolean
        get () = groups == contiguous

    val arrangements: Int
        get () = 1
}

data class Records (
    val records: List<Record>
) {
    fun dump () {
        records.forEach {
            println ("${it.conditions.map { it.symbol }} ${it.contiguous}")
        }
    }
}

object Parser {
    private fun parse (row: String): Pair<List<Condition>, List<Int>> {
        val (left, right) = row.split (" ")
        val conditions = left.map { Condition.fromSymbol (it) }
        val contiguous = right.split (",").map { it.toInt () }
        return conditions to contiguous
    }

    fun parse (rows: List<String>): Records {
        val records = mutableListOf<Record> ()
        rows.forEach {
            val (left, right) = parse (it)
            records.add (Record (left.toMutableList (), right))
        }

        return Records (records)
    }
}

fun main(args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    val records = Parser.parse (inputs.sample1)
    records.dump()
    return
}

// EOF