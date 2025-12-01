package org.juol.aoc.y2025

import org.juol.aoc.utils.*

fun String.parseRotations() =
    lines().map {
        val d = it.first()
        val n = it.drop(1).toInt()
        Pair(d, n)
    }

private fun part1(input: String): Int {
    val rotations = input.parseRotations()

    var pos = 50
    var sum = 0

    rotations.forEach {
        val (d, n) = it
        pos += if (d == 'L') -n else n
        pos = (pos + 100) % 100
        if (pos == 0) sum++
    }

    return sum
}

private fun part2(input: String): Int {
    val rotations = input.parseRotations()
    var pos = 50
    var sum = 0

    rotations.forEach {
        val (d, n) = it

        val fullRotations = n / 100
        sum += fullRotations

        val r = n % 100
        val newPos = if (d == 'L') pos - r else pos + r
        if ((d == 'L' && 0 in newPos..<pos) || (d == 'R' && 100 in pos + 1..newPos)) sum++
        pos = (newPos + 100) % 100

        println("Move $d$n to $pos with clicks $sum")
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day01")
    // 1059
    part1(input).println()
    // 6305
    part2(input).println()
}
