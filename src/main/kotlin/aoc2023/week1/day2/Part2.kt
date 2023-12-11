package aoc2023.week1.day2

import aoc2023.util.AOC

fun main (args: Array<String>) {
    val (s1, _, p) = AOC.getInputs (2)

    Parser.parse (s1).let {
        val total = it.foldRight (0) { next, acc -> next.power + acc }
        println ("sample: $total")
    }

    Parser.parse (p).let {
        val total = it.foldRight (0) { next, acc -> next.power + acc }
        println ("actual: $total")
    }
    return

}

// EOF