package aoc2023.week1.day7.part2

import aoc2023.util.hasValues
import aoc2023.util.increment

val DAY = 7

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND;
}

enum class Card (val symbol: Char) {
    CARD_J ('J'),
    CARD_2 ('2'),
    CARD_3 ('3'),
    CARD_4 ('4'),
    CARD_5 ('5'),
    CARD_6 ('6'),
    CARD_7 ('7'),
    CARD_8 ('8'),
    CARD_9 ('9'),
    CARD_T ('T'),
    CARD_Q ('Q'),
    CARD_K ('K'),
    CARD_A ('A');

    companion object {
        private val symbolsToCard = mutableMapOf<Char, Card>().apply {
            Card.values ().forEach {
                this [it.symbol] = it
            }
        }

        val exceptJoker = Card.values ().filter { it != Card.CARD_J }
        fun fromSymbol(symbol: Char): Card = symbolsToCard[symbol] ?: throw Exception ("Invalid symbol: $symbol")
    }
}

data class Hand (
    val cards: List<Card>
) {
    constructor (symbols: String): this (parse (symbols))

    override fun toString (): String {
        return StringBuffer (5).apply {
            cards.forEach {
                append (it.symbol)
            }
        }.toString ()
    }

    fun replace (index: Int, replace: Card): Hand {
        val alt = mutableListOf<Card> ().apply {
            addAll (cards)
            this[index] = replace
        }
        return Hand (alt)
    }

    val handType: HandType
        get () {
            val map = mutableMapOf<Card, Int> ().apply {
                for (card in cards) {
                    this.increment (card)
                }
            }
            return when {
                map.containsValue (5) -> HandType.FIVE_OF_A_KIND
                map.containsValue (4) -> HandType.FOUR_OF_A_KIND
                map.containsValue (3) && map.containsValue (2) -> HandType.FULL_HOUSE
                map.containsValue (3) -> HandType.THREE_OF_A_KIND
                map.hasValues (2).size == 2 -> HandType.TWO_PAIR
                map.containsValue (2) -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }

    val permutations: List<Hand>
        get () = permutations (this)

    val best: Hand
        get () {
            val options = permutations
            val sorted = permutations.sortedWith (comparator)
            return sorted.last()
        }

    companion object {
        fun parse (symbols: String): List<Card> = symbols.map { Card.fromSymbol(it) }

        fun permutations (hand: Hand, list: MutableList<Hand> = mutableListOf ()): List<Hand> {
            if (hand.cards.contains (Card.CARD_J)) {
                val i = hand.cards.indexOf (Card.CARD_J)
                Card.exceptJoker.forEach {
                    permutations (hand.replace (i, it), list)
                }
            } else {
                list.add (hand)
            }
            return list
        }

        fun compare (o1: Hand, o2: Hand): Int {
            for (i in 0 ..< o1.cards.size) {
                val c1 = o1.cards[i]
                val c2 = o2.cards[i]
                if (c1.ordinal == c2.ordinal) {
                    continue;
                } else {
                    return c1.ordinal - c2.ordinal
                }
            }
            return 0
        }

        val comparator = object: Comparator<Hand> {
            override fun compare(o1: Hand?, o2: Hand?): Int {
                o1 as Hand
                o2 as Hand
                val ht1 = o1.handType
                val ht2 = o2.handType
                return if (ht1 != ht2) {
                    ht1.ordinal - ht2.ordinal
                } else {
                    Companion.compare (o1, o2)
                }
            }

        }
    }
}

data class Round (
    val hand: Hand,
    val bid: Int
) {
    companion object {
        val comparator = object : Comparator<Round> {
            override fun compare(o1: Round?, o2: Round?): Int {
                return Hand.comparator.compare(o1!!.hand, o2!!.hand)
            }
        }
    }
}

data class Rounds (
    val rounds: List<Round>
)

object Parser {
    fun parse (rows: List<String>): Rounds {
        return Rounds (
            rows.map { parse (it) }
        )
    }

    fun parse (row: String): Round {
        val (symbols, bid) = row.trim ().split(" ")
        return Round (Hand (symbols), bid.toInt ())
    }
}
