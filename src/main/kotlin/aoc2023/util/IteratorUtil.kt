package aoc2023.util

import java.io.IOException
import java.util.ArrayList

fun <T : Any> Iterator<T>.toList (): List<T> {
    val list = ArrayList<T> ()
    while (hasNext()) {
        list.add(next())
    }
    return list
}

fun <T> Iterator<T>.expect (t: T): T {
    val next = next ()
    if (next == t) {
        return next
    } else {
        throw IOException ("Expected \"$t\"; found \"$next\"")
    }
}

fun <T> Iterator<T>.peekable () : PeekableIterator<T> = PeekableIterator<T> (this)

fun <T> Iterator<T>.logging () : Iterator<T> {
    val that = this
    return object : Iterator<T> {
        override fun hasNext(): Boolean {
            val res = that.hasNext ()
            println ("hasNext() -> $res")
            return res
        }

        override fun next(): T {
            val res = that.next ()
            println ("next() -> $res")
            return res
        }
    }
}

class PeekableIterator<T> (val wrapped: Iterator<T>): Iterator<T> {
    private var saved: MutableList<T> = mutableListOf()

    fun hasNext(index: Int = 0): Boolean {
        while (saved.size < index + 1) {
            if (wrapped.hasNext()) {
                saved.add(wrapped.next())
            } else {
                return false
            }
        }
        return true
    }

    override fun hasNext(): Boolean {
        return if (saved.isNotEmpty()) {
            true
        } else {
            wrapped.hasNext()
        }
    }

    fun peek(index: Int = 0): T {
        while (saved.size < index + 1) {
            saved.add(wrapped.next())
        }
        return saved.get(index)
    }

    override fun next(): T {
        return if (saved.isNotEmpty()) {
            saved.removeAt(0)
        } else {
            wrapped.next()
        }
    }
}