package org.juol.aoc.y2025

import org.juol.aoc.utils.*

private fun String.parseVoltage() = this.lines().map { l -> l.split("").filter { it.isNotEmpty() }.map { it.toInt() } }

private fun calc(
    input: String,
    batterySize: Int,
): Long {
    val values = input.parseVoltage()
    var sum = 0L

    values.forEach { row ->
        val final = mutableListOf<Int>()
        var current = row
        while (final.size < batterySize) {
            for (i in 9 downTo 0) {
                val idx = current.indexOf(i)
                if (idx < 0) {
                    continue
                }
                val numbersLeft = batterySize - final.size
                if (current.size - idx < numbersLeft) {
                    continue
                }
                final.add(i)
                current = current.slice(idx + 1..<current.size)
                break
            }
        }
        sum += final.joinToString(separator = "") { it.toString() }.toLong()
    }

    return sum
}

private fun part1(input: String): Long = calc(input, 2)

private fun part2(input: String): Long = calc(input, 12)

fun main() {
    val input = readInput("y2025/Day03")
    // 17207
    part1(input).println()
    // 170997883706617
    part2(input).println()
}
