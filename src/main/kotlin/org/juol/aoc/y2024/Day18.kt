package org.juol.aoc.y2024

import org.juol.aoc.utils.*

private fun parse(input: String) =
    input
        .lines()
        .map { row -> row.split(",").let { it[0].toInt() to it[1].toInt() } }

private fun part1(
    input: String,
    bytesCount: Int,
    gridSize: Int,
): Int {
    val grid = filledGrid(gridSize, gridSize, ".")
    parse(input)
        .take(bytesCount)
        .forEach { p -> grid[p] = "#" }

    val dst = grid.shortestDst(0 to 0, gridSize - 1 to gridSize - 1, "#")
    return dst
}

private fun part2(
    input: String,
    gridSize: Int,
): String {
    val grid = filledGrid(gridSize, gridSize, ".")

    val bytes = parse(input)

    for (b in bytes) {
        grid[b] = "#"
        val s = grid.shortestDst(0 to 0, gridSize - 1 to gridSize - 1, "#")
        if (s == Int.MAX_VALUE) {
            return "${b.x},${b.y}"
        }
    }

    return ""
}

fun main() {
    val input = readInput("Day18")
    // 262
    part1(input, 1024, 71).println()
    // 22,20
    part2(input, 71).println()
}
