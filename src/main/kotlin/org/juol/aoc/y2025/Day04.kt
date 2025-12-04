package org.juol.aoc.y2025

import org.juol.aoc.utils.*

private fun part1(input: String): Long {
    val grid = input.toStringGrid()
    var sum = 0L

    grid.forEach { p, v ->
        if (v == "@") {
            val paperNeighbors =
                p.adjacent().count { grid.getOrNull(it) == "@" }
            if (paperNeighbors < 4) {
                sum++
            }
        }
    }

    return sum
}

private fun part2(input: String): Long {
    val grid = input.toStringGrid()
    var sum = 0L

    var changed = true
    while (changed) {
        changed = false
        grid.forEach { p, v ->
            if (v == "@") {
                val paperNeighbors =
                    p.adjacent().count { grid.getOrNull(it) == "@" }
                if (paperNeighbors < 4) {
                    sum++
                    grid[p] = "x"
                    changed = true
                }
            }
        }
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day04")
    // 1467
    part1(input).println()
    // 8484
    part2(input).println()
}
