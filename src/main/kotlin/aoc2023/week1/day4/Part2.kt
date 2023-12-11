package aoc2023.week1.day4

import aoc2023.util.AOC

/**
 * Solution to day 4, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (4)

    Parser.parse (inputs.actual).let {
        val cards = it.cards
        for (i in cards.indices) {
            val card = cards[i]
            repeat (card.copies) { _ ->
                it.play (i)
            }
        }

        println ("total: ${it.totalCards}")
    }

    return
}


// EOF