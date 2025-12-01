package org.juol.aoc.y2024

import org.juol.aoc.utils.*
import kotlin.math.abs

private fun part1(input: String): Int {
    val (left, right) = input.toIntGrid().columns().map { it.sorted() }
    val sum = left.zip(right).sumOf { (l, r) -> abs(l - r) }
    return sum
}

private fun part2(input: String): Int {
    val (left, right) = input.toIntGrid().columns()
    val sum = left.sumOf { l -> right.sumOf { r -> if (l == r) l else 0 } }
    return sum
}

fun main() {
    val input = readInput("y2024/Day01")
    // 2192892
    part1(input).println()
    // 22962826
    part2(input).println()
}
