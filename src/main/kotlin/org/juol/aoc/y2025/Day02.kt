package org.juol.aoc.y2025

import org.juol.aoc.utils.*

private fun String.parseRanges() = this.split(",").map { it.split("-") }.map { Pair(it[0].toLong(), it[1].toLong()) }

private fun part1(input: String): Long {
    fun isInvalid(num: Long): Boolean {
        val str = num.toString()
        if (str.length.isOdd()) {
            return false
        }
        val ch = str.chunked(str.length / 2)
        val repeats = ch[0] == ch[1]

        return repeats
    }

    val ranges = input.parseRanges()
    var sum = 0L

    ranges.forEach { (start, end) ->
        for (num in start..end) {
            if (isInvalid(num)) {
                sum += num
            }
        }
    }

    return sum
}

private fun part2(input: String): Long {
    fun isInvalid(num: Long): Boolean {
        val str = num.toString()
        val repeats =
            str.chunkAllLengths().any { ch ->
                ch.all { it == ch.first() }
            }

        return repeats
    }

    val ranges = input.parseRanges()
    var sum = 0L

    ranges.forEach { (start, end) ->
        for (num in start..end) {
            if (isInvalid(num)) {
                sum += num
            }
        }
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day02")
    // 8576933996
    part1(input).println()
    // 25663320831
    part2(input).println()
}
