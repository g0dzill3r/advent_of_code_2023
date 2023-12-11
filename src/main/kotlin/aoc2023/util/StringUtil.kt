package aoc2023.util

fun String.replaceAll (map: List<Pair<String, String>>): String {
    var tmp = this
    for ((key, value) in map) {
        tmp = tmp.replace (key, value)
    }
    return tmp
}

val String.firstLast: String
    get () = "${this[0]}${this[this.length - 1]}"

val String.digits: String
    get () = this.filter { it.isDigit () }

// EOF