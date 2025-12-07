package org.juol.aoc.y2025

import org.juol.aoc.utils.*
import java.math.BigDecimal

private fun part1(input: String): Long {
    val grid = input.toStringGrid(" ")
    var sum = 0L

    grid.columns().forEach { col ->
        val operator = col.last()
        val numbers = col.dropLast(1).map { it.toLong() }
        val result =
            when (operator) {
                "*" -> numbers.reduce { acc, n -> acc * n }
                "+" -> numbers.reduce { acc, n -> acc + n }
                else -> error("Unknown operator: $operator")
            }
        sum += result
    }

    return sum
}

private fun part2(input: String): BigDecimal {
    val lines = input.lines()
    val length = lines.maxOf { it.length }
    val columns = Array(length) { "" }
    lines.forEach { line ->
        line.forEachIndexed { i, c ->
            columns[i] += c
        }
    }
    var buff: MutableList<String> = mutableListOf()
    val columnGroups = mutableListOf<List<String>>()

    columns.forEach { c ->
        if (c.isBlank()) {
            columnGroups.add(buff)
            buff = mutableListOf()
        } else {
            val last = c.lastOrNull()
            if (last == '*' || last == '+') {
                buff.add(last.toString())
                buff.add(c.dropLast(1).trim())
            } else {
                buff.add(c.trim())
            }
        }
    }
    columnGroups.add(buff)

    var sum = BigDecimal.ZERO

    columnGroups.forEach { group ->
        val operator = group.first()
        val numbers = group.drop(1).map { it.toLong() }

        val result: Long =
            when (operator) {
                "*" -> numbers.reduce { acc, n -> acc * n }
                "+" -> numbers.reduce { acc, n -> acc + n }
                else -> error("Unknown operator: $operator")
            }
        sum += BigDecimal.valueOf(result)
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day06")
    // 6503327062445
    part1(input).println()
    // 9640641878593
    part2(input).println()
}
