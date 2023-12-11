package aoc2023.week1.day5

import aoc2023.util.BigIntegerRange
import aoc2023.util.max
import aoc2023.util.min
import java.math.BigInteger

/**
 * Data structure for a set of intervals.
 */

data class Intervals (
    val intervals: List<Interval>
) {
    fun sort (): Intervals = Intervals (intervals.sortedBy { it.start })

    fun coalesce (): Intervals {
        val sorted = sort ()
        var tmp = intervals.toMutableList()
        var i = 0
        while (i < tmp.size - 1) {
            if (tmp[i].overlaps (tmp[i + 1])) {
                tmp[i] = tmp[i].merge (tmp[i + 1])
                tmp.removeAt (i + 1)
            } else {
                i ++
            }
        }
        return Intervals (tmp)
    }

    fun split (breaks: List<BigInteger>): Intervals {
        val tmp = intervals.toMutableList()
        outer@for (brk in breaks) {
            for (index in tmp.indices) {
                val interval = tmp[index]
                if (interval.contains (brk)) {
                    if (brk != interval.start) {
                        tmp[index] = Interval (interval.start, brk - interval.start)
                        tmp.add(index + 1, Interval (brk, interval.length - (brk - interval.start)))
                    }
                    continue@outer
                }
            }
        }
        return Intervals (tmp)
    }

    fun dump () {
        val start = intervals.minOf { it.start }.minus (2.toBigInteger())
        val end = intervals.maxOf { it.end }.plus (2.toBigInteger())
        intervals.forEach {
            for (i in BigIntegerRange(start, end)) {
                if (it.contains (i)) {
                    print ("*")
                } else {
                    print ("-")
                }
            }
            println ()
        }
        for (i in BigIntegerRange(start, end)) {
            print (i.mod (10.toBigInteger()).toInt ())
        }
        println ()
        return
    }

    override fun toString (): String {
        return StringBuffer ().apply {
            val iter = intervals.iterator()
            while (iter.hasNext ()) {
                append (iter.next ())
                if (iter.hasNext ()) {
                    append (", ")
                }
            }
        }.toString ()
    }
}

/**
 * A single interval, not inclusive of the end value.
 *
 * e.g. [start, end)
 */

data class Interval (
    val start: BigInteger,
    val length: BigInteger,
    val end: BigInteger = start.add (length)
) {
    constructor (start: Int, length: Int) : this (start.toBigInteger (), length.toBigInteger ())

    fun contains (value: BigInteger): Boolean = value >= start && value < end
    fun contains (value: Int): Boolean = contains (value.toBigInteger())
    fun contains (other: Interval): Boolean = start < other.start && end >= other.end

    fun overlaps (other: Interval): Boolean {
        return other.contains (start) || other.contains (end.minus (1.toBigInteger())) || contains (other)
    }

    fun merge (other: Interval): Interval {
        if (! overlaps (other)) {
            throw Exception ("Cannot merge non-overlapping intervals.")
        }
        return Interval (min (start, other.start), max (end, other.end))
    }

    override fun toString (): String {
        return "[$start, $end)"
    }
}

fun main (args: Array<String>) {
    val list = mutableListOf (
        Interval (10, 2),
        Interval (5, 3),
        Interval (3, 2),
        Interval (1, 2)
    )
    val intervals = Intervals (list)
    println (intervals)

    val sorted = intervals.sort ()
    println (sorted)

    // Test contains

    fun d (i0: Interval, i1: Interval) {
        val start = min (i0.start, i1.start).minus (2.toBigInteger())
        val end = max (i0.end, i1.end).plus (2.toBigInteger())
        val buf = StringBuffer ()
        for (i in start.toInt() ..< end.toInt ()) {
            when {
                i0.contains (i) && i1.contains (i) -> buf.append ("+")
                i0.contains (i) -> buf.append ("-")
                i1.contains (i) -> buf.append ("|")
                else -> buf.append (".")
            }
        }
        println (buf.toString ())
    }

    fun f (s0: Int, l0: Int, s1: Int, l1: Int) {
        val i0 = Interval (s0, l0)
        val i1 = Interval (s1, l1)
        println ("$i0 :${i0.length} $i1 :${i1.length} -> ${i0.overlaps (i1)}")
        run {
            val i = Intervals (listOf (i0, i1))
            i.dump ()
            if (i0.overlaps (i1)) {
                println ("Coalesced")
                i.coalesce().dump ()
            }
        }

        println ()
    }

    f (0,5, 10, 5)
    f (0,5, 2, 5)
    f (0,5, 4, 5)
    f (0,5, 5, 5)
    f (0,5, 6, 5)

    run {
        val i = Intervals (
            listOf (
                Interval (0, 10),
                Interval (5, 10),
                Interval (10, 10),
                Interval (15, 10)
            )
        )
        i.dump ()
        val i2 = i.coalesce()
        i2.dump ()
        val breaks = listOf (5, 6, 7)
        val i3 = i2.split (breaks.map { it.toBigInteger () })
        i3.dump ()
    }

    return
}

// EOF