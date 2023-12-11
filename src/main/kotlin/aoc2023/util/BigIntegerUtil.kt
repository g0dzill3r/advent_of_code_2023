package aoc2023.util

import java.math.BigInteger

data class BigIntegerRange (
    val start: BigInteger,
    val end: BigInteger
): Iterable<BigInteger> {
    override fun iterator(): Iterator<BigInteger> = BigIntegerIterator (start, end)
}

class BigIntegerIterator (val start: BigInteger, val end: BigInteger): Iterator<BigInteger> {
    private var current = start

    override fun hasNext(): Boolean {
        return current < end
    }

    override fun next(): BigInteger {
        val tmp = current
        current = current.add (1.toBigInteger ())
        return tmp
    }
}

fun min (a: BigInteger, b: BigInteger) = if (a <= b) a else b
fun max (a: BigInteger, b: BigInteger) = if (a >= b) a else b

fun main (args: Array<String>) {
    val start = 1
    val end = 10
    val range = BigIntegerRange (start.toBigInteger(), end.toBigInteger())
    for (i in range) {
        println (i)
    }
    return
}

// EOF