package aoc2023.util

fun <T> absorb (func: () -> T): T? {
    return try {
        func ()
    }
    catch (e: Throwable) {
        null
    }
}

fun Int.repeat (func: (Int) -> Unit) {
    for (i in 0 ..< this) {
        func (i)
    }
}

// EOF