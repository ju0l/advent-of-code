package org.juol.aoc.y2024

import org.juol.aoc.utils.*
import kotlin.math.floor

private fun ruleApplies(
    change: List<Int>,
    rule: List<Int>,
): Boolean {
    val (a, b) = rule
    return change.contains(a) && change.contains(b) && change.indexOf(a) > change.indexOf(b)
}

private fun isCorrect(
    change: List<Int>,
    rules: List<List<Int>>,
): Boolean {
    for (rule in rules) {
        val (a, b) = rule
        if (ruleApplies(change, rule)) {
            return false
        }
    }
    return true
}

private fun middleNumber(change: List<Int>): Int = change[floor(change.size / 2.0).toInt()]

private fun parseInput(input: String): Pair<List<List<Int>>, List<List<Int>>> {
    val rules = input.split("\n\n")[0].lines().map { it.split("|").map { it.toInt() } }
    val changes = input.split("\n\n")[1].lines().map { it.split(",").map { it.toInt() } }
    return rules to changes
}

private fun part1(input: String): Int {
    val (rules, changes) = parseInput(input)

    val sum = changes.filter { change -> isCorrect(change, rules) }.sumOf { middleNumber(it) }
    return sum
}

private fun part2(input: String): Int {
    val (rules, changes) = parseInput(input)

    fun fixChange(change: List<Int>): List<Int> {
        val fixedChange = change.toMutableList()
        for (rule in rules) {
            val (a, b) = rule
            if (ruleApplies(fixedChange, rule)) {
                val aIndex = fixedChange.indexOf(a)
                val bIndex = fixedChange.indexOf(b)
                fixedChange.removeAt(bIndex)
                fixedChange.add(aIndex, b)
            }
        }
        if (!isCorrect(fixedChange, rules)) {
            return fixChange(fixedChange)
        }
        return fixedChange
    }

    val sum = changes.filter { change -> !isCorrect(change, rules) }.sumOf { middleNumber(fixChange(it)) }
    return sum
}

fun main() {
    val input = readInput("y2024/Day05")
    // 5208
    part1(input).println()
    // 6732
    part2(input).println()
}
