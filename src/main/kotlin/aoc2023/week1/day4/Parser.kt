package aoc2023.week1.day4

import aoc2023.util.AOC
import java.io.IOException
import java.util.regex.Pattern

val DAY = 4

data class Cards (
    val cards: List<Card>
) {
    fun play (index: Int) {
        val card = cards[index]
        val hits = card.hits
        if (hits > 0) {
            for (i in index + 1 ..index + hits) {
                if (i < cards.size) {
                    cards[i].copy ()
                }
            }
        }
        return
    }

    fun dump () {
        println ("=== ${cards.size} cards ===")
        cards.forEach {
            println ("card ${it.number}: ${it.copies}")
        }
    }

    val totalCards: Int
        get () = cards.foldRight (0) { next, acc -> next.copies + acc }
}

data class Card (
    val number: Int,
    val winning: Set<Int>,
    val mine: Set<Int>,
    var copies: Int = 1
) {
    fun copy (increment: Int = 1): Int {
        copies += increment
        return copies
    }

    val overlap: Set<Int>
        get () = winning.intersect (mine)

    val hits: Int
        get () = overlap.count ()

    val score: Int
        get () {
            return when (hits) {
                0 -> 0
                else -> {
                    var result = 1
                    for (i in 0 ..< hits - 1) {
                        result *= 2
                    }
                    result
                }
            }
        }
}

object Parser {
    fun parse (rows: List<String>): Cards {
        val cards = mutableListOf<Card> ()
        rows.forEach {
            val match = pattern.matcher (it)
            if (! match.matches()) {
                throw IOException ("Invalid input: $it")
            }
            val card = match.group (1).trim ()
            val (winning, mine) = match.group (2).split ("|").map {
                it.trim ().split (Pattern.compile ("\\s+")).map {
                    it.toInt ()
                }
            }
            cards.add (Card (card.toInt (), winning.toSet (), mine.toSet ()))
        }
        return Cards (cards)
    }

    private val pattern = Pattern.compile ("^Card (.+): (.+)$")
}

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)
    val cards = Parser.parse (inputs.sample1)
    cards.cards.forEach {
        println ("CARD ${it.number}")
        println ("winning - ${it.winning}")
        println ("mine    - ${it.mine}")
        println ("overlap - ${it.overlap}")
        println ("hits    - ${it.hits}")
        println ("score  - ${it.score}")
    }
    return
}

// EOF