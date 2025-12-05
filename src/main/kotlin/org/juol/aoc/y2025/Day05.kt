package org.juol.aoc.y2025

import org.juol.aoc.utils.*
import kotlin.math.max

private data class IngredientRanges(
    val all: List<Long>,
    val fresh: List<Pair<Long, Long>>,
)

private fun String.parseIngredients(): IngredientRanges {
    val all: MutableList<Long> = mutableListOf()
    val fresh: MutableList<Pair<Long, Long>> = mutableListOf()
    var split = false
    lineSequence().forEach { line ->
        if (line.isBlank()) {
            split = true
        } else if (!split) {
            val (a, b) = line.split("-").map { it.toLong() }
            fresh.add(a to b)
        } else {
            all.add(line.toLong())
        }
    }

    return IngredientRanges(all, fresh)
}

private fun part1(input: String): Int {
    val (ingredients, fresh) = input.parseIngredients()
    return ingredients.count { ingredient ->
        fresh.any { (a, b) -> ingredient in a..b }
    }
}

private fun part2(input: String): Long {
    val (_, fresh) = input.parseIngredients()
    val merged = mutableListOf<Pair<Long, Long>>()

    fresh.sortedBy { it.first }.forEach { p ->
        val prev = merged.lastOrNull()
        if (prev == null || p.first > prev.second) {
            merged.add(p)
        } else {
            merged[merged.lastIndex] = prev.first to max(prev.second, p.second)
        }
    }

    return merged.sumOf { it.second - it.first + 1 }
}

fun main() {
    val input = readInput("y2025/Day05")
    // 613
    part1(input).println()
    // 336495597913098
    part2(input).println()
}
