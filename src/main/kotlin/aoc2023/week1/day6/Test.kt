package aoc2023.week1.day6

fun main (args: Array<String>) {
    val time = 71530.toBigInteger ()
    val distance = 940200.toBigInteger ()

    for (hold in 13 .. 14) {
        val score = (time - hold.toBigInteger()) * hold.toBigInteger()
        println ("$hold: $score")
    }
    return
}

// EOF