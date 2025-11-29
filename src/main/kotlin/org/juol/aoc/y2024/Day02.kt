package org.juol.aoc.y2024

import org.juol.aoc.utils.*
import kotlin.math.abs

private fun isRowSafe(row: List<Int>): Boolean {
    var inc = 0
    var desc = 0
    row.zipWithNext().forEach { (a, b) ->
        if (a == b || abs(a - b) > 3) return false
        if (a < b) {
            if (desc != 0) return false
            inc++
        } else {
            if (inc != 0) return false
            desc++
        }
    }
    return true
}

private fun part1(input: String): Int {
    val grid = input.toIntGrid()
    val safe = grid.rows().count { isRowSafe(it) }
    return safe
}

private fun part2(input: String): Int {
    val grid = input.toIntGrid()
    val safe =
        grid.rows().count { row ->
            if (isRowSafe(row)) return@count true

            for (i in row.indices) {
                val r = row.toMutableList().also { it.removeAt(i) }
                if (isRowSafe(r)) return@count true
            }

            false
        }

    return safe
}

fun main() {
    val input = readInput("Day02")
    // 390
    part1(input).println()
    // 439
    part2(input).println()
}
