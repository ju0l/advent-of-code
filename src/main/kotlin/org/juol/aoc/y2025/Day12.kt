package org.juol.aoc.y2025

import org.juol.aoc.utils.*

private fun part1(input: String): Long {
    val parts = input.split("\n\n")

    val presentSizes =
        parts.dropLast(1).dropLast(1).associate { present ->
            val (index, shape) = present.split(":")
            val size = shape.count { it == '#' }
            index.toInt() to size
        }

    val possible = mutableMapOf<Double, String>()
    var sum = 0L
    parts.last().lineSequence().filter { it.isNotBlank() }.forEach { region ->
        val (size, presentCounts) = region.split(": ")

        val totalGridSize = size.split("x").map { it.toInt() }.let { (a, b) -> a * b }
        val totalPresentSize =
            presentCounts
                .split(" ")
                .mapIndexed { i, n -> n.toInt() * (presentSizes[i] ?: 0) }
                .sum()
        val ratio = totalGridSize.toDouble() / totalPresentSize
        if (ratio >= 1.3) {
            sum++
        } else if (ratio >= 1.0) {
            possible[ratio] = "$totalGridSize-$totalPresentSize"
        }
    }

    // sum could be extended by some of these possible regions
    possible.toSortedMap().forEach { (key, value) -> println(String.format("%.3f: %s", key, value)) }
    return sum
}

fun main() {
    val input = readInput("y2025/Day12")
    // 548
    part1(input).println()
}
