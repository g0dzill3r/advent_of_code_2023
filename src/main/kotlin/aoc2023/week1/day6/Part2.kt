package aoc2023.week1.day6

import aoc2023.util.AOC
import java.math.BigInteger

private val ONE = 1.toBigInteger ()
private val TWO = 2.toBigInteger ()


/**
 * Solution to day 6, part 2.
 */

fun main (args: Array<String>) {
    val inputs = AOC.getInputs (DAY)

    Parser.parse (inputs.actual).apply {
        val time = this.races.foldRight ("") { next, acc -> "" + next.time + acc }.toBigInteger()
        val distance = this.races.foldRight ("") { next, acc -> "" + next.distance + acc }.toBigInteger()

        val low = findLow (time, distance)
        val high = findHigh (time, distance)

        val possible = time - low - (time - high)
        println (possible)
    }

    return
}

private fun findLow (time: BigInteger, distance: BigInteger, curr: BigInteger = time / 2.toBigInteger (), span: BigInteger = curr): BigInteger {
    val calculateScore = { hold: BigInteger -> (time - hold) * hold }
    val score = calculateScore (curr)
    val adjust = span.divide (TWO)

    return when {
        score <= distance -> {
            if (calculateScore (curr + ONE) > distance) {
                curr + ONE
            } else {
                findLow (time, distance, curr + adjust, adjust)
            }
        }
        score > distance -> {
            if (calculateScore (curr - ONE) <= distance) {
                curr
            } else {
                findLow (time, distance, curr - adjust, adjust)
            }
        }
        else -> throw Exception ("Unreachable")
    }
}

private fun findHigh (time: BigInteger, distance: BigInteger, curr: BigInteger = time / 2.toBigInteger (), span: BigInteger = curr): BigInteger {
    val calculateScore = { hold: BigInteger -> (time - hold) * hold }
    val score = calculateScore (curr)
    val adjust = span.divide (TWO)

    return when {
        score >= distance -> {
            if (calculateScore (curr + ONE) < distance) {
                curr + ONE
            } else {
                findHigh (time, distance, curr + adjust, adjust)
            }
        }
        score < distance -> {
            if (calculateScore (curr - ONE) >= distance) {
                curr
            } else {
                findHigh (time, distance, curr - adjust, adjust)
            }
        }
        else -> throw Exception ("Unreachable")
    }
}


// EOF