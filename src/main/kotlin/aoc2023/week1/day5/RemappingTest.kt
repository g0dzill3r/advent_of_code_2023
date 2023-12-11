package aoc2023.week1.day5

/**
 * Test the remapping functionality.
 */

fun main (args: Array<String>) {

    // Some source intervals

    val intervals = Intervals (
        listOf (
            Interval (0, 5),
            Interval (10, 5),
            Interval (20, 5)

        )
    )
    intervals.dump ()

    // A set of remappings

    val mappings = Mappings (
        listOf (
            Mapping (103, 3, 10),
            Mapping (113, 13, 10)
        )
    )
    println (mappings)

    // Remap the intervals

    val mapped = mappings.map (intervals)
    mapped.dump ()

    return
}

// EOF