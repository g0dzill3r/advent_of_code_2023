package aoc2023.week3.day15

import aoc2023.util.repeat

class Boxes {
    val slots = mutableListOf<MutableList<Entry>>().apply {
        256.repeat {
            add(mutableListOf<Entry>())
        }
    }.toList()

    fun process (entry: Entry) {
        val lenses = slots[entry.slot]
        when (entry.operation) {
            Op.DASH -> {
                val already = lenses.indexOfFirst { it.symbol == entry.symbol }
                if (already != -1) {
                    lenses.removeAt (already)
                }
            }
            Op.EQUALS -> {
                val already = lenses.indexOfFirst { it.symbol == entry.symbol }
                if (already == -1) {
                    lenses.add (entry)
                } else {
                    lenses.removeAt (already)
                    lenses.add (already, entry)
                }
            }
        }
        return
    }

    val power: Int
        get () {
            var total = 0
            slots.forEachIndexed { i, lenses ->
                lenses.forEachIndexed { j, entry ->
                    total += (i + 1) * (j + 1) * entry.value
                }
            }
            return total
        }

    fun dump () {
        slots.forEachIndexed { i, els ->
            if (els.isNotEmpty()) {
                println ("Box $i: $els")
            }
        }
        return
    }
}


// EOF