package org.juol.aoc.y2024

import org.juol.aoc.utils.*

private fun part1(input: String): Int {
    val chars = input.toStringGrid()
    val search = "XMAS"
    var count = 0
    var stack = ""

    fun checkStack(c: String) {
        stack += c
        if (search == stack) {
            count++
            stack = ""
        }
        while (!search.startsWith(stack)) {
            stack = stack.drop(1)
        }
    }

    chars.rows().forEach { row ->
        row.forEach { c ->
            checkStack(c)
        }
        stack = ""
        row.reversed().forEach { c ->
            checkStack(c)
        }
        stack = ""
    }
    chars.columns().forEach { column ->
        column.forEach { c ->
            checkStack(c)
        }
        stack = ""
        column.reversed().forEach { c ->
            checkStack(c)
        }
        stack = ""
    }
    chars.leftDiagonals().forEach { diagonal ->
        diagonal.forEach { c ->
            checkStack(c)
        }
        stack = ""
        diagonal.reversed().forEach { c ->
            checkStack(c)
        }
        stack = ""
    }
    chars.rightDiagonals().forEach { diagonal ->
        diagonal.forEach { c ->
            checkStack(c)
        }
        stack = ""
        diagonal.reversed().forEach { c ->
            checkStack(c)
        }
        stack = ""
    }
    return count
}

private fun part2(input: String): Int {
    val chars = input.toStringGrid()
    var count = 0
    val search = "MAS"

    fun checkStack(p: Point) {
        val neighbours: List<List<Point>> =
            listOf(
                listOf(-1 to -1, 0 to 0, 1 to 1),
                listOf(1 to 1, 0 to 0, -1 to -1),
                listOf(-1 to 1, 0 to 0, 1 to -1),
                listOf(1 to -1, 0 to 0, -1 to 1),
            )
        neighbours
            .count { n ->
                n.joinToString("") { d -> chars.getOrNull(p + d).orEmpty() } == search
            }.let {
                if (it >= 2) {
                    count++
                }
            }
    }

    chars.forEach { p, c -> checkStack(p) }
    return count
}

fun main() {
    val input = readInput("Day04")
    // 2454
    part1(input).println()
    // 1858
    part2(input).println()
}
