package aoc2023.week1.day7.part2

fun main (args: Array<String>) {
    println ("HAND")
    val hand = Hand ("234JJ")
    println (hand)

    println ("REPLACE")
    println (hand.replace (3, Card.CARD_Q))

    println ("PERMUTE")
    val options = hand.permutations
    options.forEach {
        println (" - $it - ${it.handType}")
    }
    val best = hand.best
    println ("best: ${best} - ${best.handType}")
    println ("${options.size} options")
    return
}

// EOF