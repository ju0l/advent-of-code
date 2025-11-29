package org.juol.aoc.y2024

import org.juol.aoc.utils.*

private fun parse(input: String): Triple<Grid<String>, Point, Point> {
    val grid = input.toStringGrid()
    val start = grid.indexOf("S") ?: throw IllegalArgumentException("No start found")
    val end = grid.indexOf("E") ?: throw IllegalArgumentException("No end found")
    grid[start] = "."
    grid[end] = "."

    return Triple(grid, start, end)
}

private fun findMinPaths(
    grid: Grid<String>,
    start: Point,
    end: Point,
) = grid.findMinPaths(start, end, "#", Direction.RIGHT) { currentDst, _, _, fromDir, toDir ->
    currentDst + 1 + (if (fromDir == toDir) 0 else 1000)
}

private fun part1(input: String): Int {
    val (grid, start, end) = parse(input)
    val paths = findMinPaths(grid, start, end)
    return paths.first().dst
}

private fun part2(input: String): Int {
    val (grid, start, end) = parse(input)
    val paths = findMinPaths(grid, start, end)

    val sum =
        paths
            .map { path -> path.path.map { it.p } }
            .flatten()
            .toSet()
            .size
    return sum
}

fun main() {
    val input = readInput("Day16")
    // 99488
    part1(input).println()
    // 516
    part2(input).println()
}
