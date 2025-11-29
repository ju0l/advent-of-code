package org.juol.aoc.y2024

import org.juol.aoc.utils.*

private val NUMBERS_REGEX = Regex("""mul\((\d+),(\d+)\)""")

private fun part1(input: String): Int {
    val sum =
        NUMBERS_REGEX.findAll(input).sumOf {
            it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
    return sum
}

private fun part2(input: String): Int {
    val parts =
        Regex("""mul\(\d+,\d+\)|(don't\(\))|(do\(\))""")
            .findAll(input)
            .map { it.value }
            .toList()

    var sum = 0
    var enabled = true
    for (part in parts) {
        if (part == "do()") {
            enabled = true
        } else if (part == "don't()") {
            enabled = false
        } else if (enabled) {
            sum +=
                NUMBERS_REGEX
                    .find(part)!!
                    .groupValues
                    .drop(1)
                    .map { it.toInt() }
                    .let { (a, b) -> a * b }
        }
    }

    return sum
}

fun main() {
    val input = readInput("Day03")
    // 157621318
    part1(input).println()
    // 79845780
    part2(input).println()
}
