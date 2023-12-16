package aoc2023.week2.day13

import aoc2023.util.AOC
import aoc2023.week1.day3.Coordinate2

/**
 * Solution to day 13, part 2.
 */

fun main () {
    val inputs = AOC.getInputs (DAY)
    val patterns = Parser.parse (inputs.sample1)
//    val patterns = Parser.parse (inputs.actual)

//    patterns.grids.forEach { grid ->
//        dump (grid)
//        println (grid.seams)
//        println (grid.possibilities)
//        println ()
//    }

    val grid = patterns.grids[1]
    dump (grid)

    val alternates = grid.possibilities
    println (alternates)

    println (grid.alternate)


    return
}

fun dump (grid: Grid) {
    val seam = grid.seams

    // Print the header

    if (seam.second != null) {
        print ("       ")
        for (col in 0..<grid.width) {
            if (col in seam.second!!..seam.second!! + 1) {
                print('v')
            } else {
                print(' ')
            }
        }
        println()
    }

    // Print the body

    for (row in 0 ..< grid.height) {
        val rstr = String.format (" %2d ", row + 1)
        val rmark = if (seam.first != null && row in seam.first!!.. seam.first!! + 1) " > " to " < " else "   " to "   "
        print (rstr)
        print (rmark.first)
        for (col in 0 ..< grid.width) {
            val tile = grid.getTile (row, col)
            print (tile.symbol)
        }
        print (rmark.second)
        println (rstr)
    }

    // Print the footer

    if (seam.second != null) {
        print("       ")
        for (col in 0..<grid.width) {
            if (col in seam.second!!..seam.second!! + 1) {
                print('^')
            } else {
                print(' ')
            }
        }
        println()
    }
    println (grid.seams)
    return
}

// EOF