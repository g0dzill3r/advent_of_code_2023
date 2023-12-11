package aoc2023.util

fun <T> MutableMap<T, Int>.increment (t: T, value: Int = 1): Int {
    val current = this[t] ?: 0
    this[t] = current + value
    return this[t]!!
}

fun <T, S> Map<T, S>.hasValues (value: S): List<T> {
    return entries.filter { (t, s) -> s == value }.map { (t, s) -> t }
}

fun main (args: Array<String>) {
    val map = mutableMapOf<String, Int> ()
    map.increment ("foo")
    map.increment ("bar", 2)
    map.increment ("baz")
    println (map)
    return
}

// EOF