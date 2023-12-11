package aoc2023.week1.day7.part2

import aoc2023.util.AOC

/**
 * Solution to day 7, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse(inputs.actual).apply {

        // Calculate the best hand with the jokers replaced

        val unsorted = rounds.map {
            Triple (it.hand, it.hand.best, it.bid)
        }
        unsorted.forEach {
            println (it)
        }

        // Now sort according to the sort rules

        val comparator = object: Comparator<Triple<Hand, Hand, Int>> {
            override fun compare(o1: Triple<Hand, Hand, Int>?, o2: Triple<Hand, Hand, Int>?): Int {
                o1 !!
                o2 !!
                val t1 = o1.second.handType
                val t2 = o2.second.handType
                if (t1 != t2) {
                    return t1.ordinal - t2.ordinal
                } else {
                    return Hand.compare (o1.first, o2.second)
                }
            }
        }

        val sorted = unsorted.sortedWith (comparator)
        sorted.forEach { (orig, best) ->
            println ("$orig -> $best - ${best.handType}")
        }

        val total = sorted.foldRightIndexed (0.toBigInteger ()) { i, next, acc ->
            acc + (next.third * (i + 1)).toBigInteger()
        }
        println (total)
    }

    return
}

// EOF