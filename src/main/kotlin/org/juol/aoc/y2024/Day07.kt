package org.juol.aoc.y2024

import org.juol.aoc.utils.*
import java.math.BigInteger

private fun count(
    input: List<Pair<Long, List<Long>>>,
    operators: List<String>,
): BigInteger {
    var sum = BigInteger.ZERO
    top@ for ((key, value) in input) {
        val ops =
            cartesianProduct(
                operators,
                operators,
                *(4..value.size).map { operators }.toTypedArray(),
            )
        for (op in ops) {
            val v: Long =
                value.reduceIndexed { i, a, b ->
                    when (op[i - 1]) {
                        "+" -> a + b
                        "*" -> a * b
                        "||" -> "$a$b".toLong()
                        else -> throw IllegalArgumentException("Unknown operator")
                    }
                }
            if (v == key) {
                sum += v.toBigInteger()
                continue@top
            }
        }
    }

    return sum
}

private fun parse(input: String) =
    input.lines().map { row ->
        val (key, value) = row.split(": ")
        key.toLong() to value.split(" ").map { it.toLong() }
    }

private fun part1(input: String): BigInteger = count(parse(input), listOf("+", "*"))

private fun part2(input: String): BigInteger = count(parse(input), listOf("+", "*", "||"))

fun main() {
    val input = readInput("Day07")
    // 4122618559853
    part1(input).println()
    // 227615740238334
    part2(input).println()
}
