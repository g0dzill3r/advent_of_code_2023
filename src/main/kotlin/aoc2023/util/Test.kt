package aoc2023.util

fun main (args: Array<String>) {
    println ("hi");

    val path = "week1/day1/p2.txt"
    val str = ResourceUtil.getAsString(path)
    println (str)
}

// EOF