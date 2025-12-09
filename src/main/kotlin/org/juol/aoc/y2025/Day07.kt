package org.juol.aoc.y2025

import org.juol.aoc.utils.*

private const val START = "S"
private const val SPLIT = "^"
private const val PATH = "|"

private fun part1(input: String): Long {
    val grid = input.toStringGrid()
    var split = 0L

    for (y in 0..<grid.height) {
        for (x in 0..<grid.width) {
            val cell = grid.getOrNull(x, y)
            if (cell == START || cell == PATH) {
                if (grid.getOrNull(x, y + 1) == SPLIT) {
                    grid.setSafe(x + 1, y + 1, PATH)
                    grid.setSafe(x - 1, y + 1, PATH)
                    split++
                } else {
                    grid.setSafe(x, y + 1, PATH)
                }
            }
        }
    }

    return split
}

private fun part2(input: String): Long {
    val grid = input.toStringGrid()
    val memo = mutableMapOf<Point, Long>()

    fun score(p: Point): Long {
        val below = p + (0 to 1)

        if (below !in grid) return 1
        memo[p]?.let { return it }
        val result =
            if (grid[below] == SPLIT) {
                score(p + (-1 to 1)) + score(p + (1 to 1))
            } else {
                score(below)
            }
        memo[p] = result
        return result
    }

    val startPoint = grid.findIndex { _, string -> START == string }!!
    return score(startPoint)
}

fun main() {
    val input = readInput("y2025/Day07")
    // 1553
    part1(input).println()
    // 15811946526915
    part2(input).println()
}
