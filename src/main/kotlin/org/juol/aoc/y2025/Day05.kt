package org.juol.aoc.y2025

import org.juol.aoc.utils.*

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
    var sum = 0L
    val ranges =
        fresh
            .mapIndexedNotNull { i, p ->
                var (a, b) = p
                fresh.subList(0, i).forEach { (c, d) ->
                    if (a >= c && b <= d) {
                        return@mapIndexedNotNull null
                    }
                    if (a in c..d) {
                        a = d + 1
                    } else if (b in c..d) {
                        b = c - 1
                    }
                }
                Pair(a, b)
            }
    ranges.forEachIndexed { i, (a, b) ->
        var excluded = 0L
        ranges.subList(0, i).forEach { (c, d) ->
            if (a <= c && b >= d) {
                excluded = d - c + 1
            }
        }
        sum += (b - a + 1) - excluded
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day05")
    // 613
    part1(input).println()
    // 336495597913098
    part2(input).println()
}
