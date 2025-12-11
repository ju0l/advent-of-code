package org.juol.aoc.y2025

import org.chocosolver.solver.Model
import org.chocosolver.solver.variables.IntVar
import org.juol.aoc.utils.*
import kotlin.math.min

private data class InputLine(
    val diagramBitmask: Int,
    val buttonsBitmask: List<Int>,
    val buttons: List<List<Int>>,
    val joltage: List<Int>,
)

private fun parseMachines(input: String): Sequence<InputLine> =
    input.lineSequence().map { line ->
        val words = line.split(" ")
        var diagramBitmask = 0
        words
            .first()
            .let { it.substring(1, it.length - 1).forEachIndexed { i, c -> if (c == '#') diagramBitmask += 1 shl i } }
        val buttons =
            words
                .subList(1, words.size - 1)
                .map { b -> b.substring(1, b.length - 1).split(",").map { it.toInt() } }
        val buttonsBitmask =
            buttons.map { ns -> ns.fold(0) { acc, x -> acc or (1 shl x) } }
        val joltage =
            words.last().let { j -> j.substring(1, j.length - 1).split(",").map { it.toInt() } }

        InputLine(diagramBitmask, buttonsBitmask, buttons, joltage)
    }

private fun part1(input: String): Long {
    var sum = 0L

    parseMachines(input).forEach { (diagramBitmask, buttonsBitmask) ->
        var score = buttonsBitmask.size
        for (a in 0..<(1 shl buttonsBitmask.size)) {
            var an = 0
            var aScore = 0
            for (i in buttonsBitmask.indices) {
                if (((a shr i) and 1) == 1) {
                    an = an xor buttonsBitmask[i]
                    aScore++
                }
            }
            if (an == diagramBitmask) score = min(score, aScore)
        }
        sum += score
    }

    return sum
}

private fun part2(input: String): Long {
    var sum = 0L

    parseMachines(input).forEach { (_, buttonsBitmask, buttons, joltage) ->
        val model = Model()
        val vars: List<IntVar> = List(buttonsBitmask.size) { model.intVar("B$it", 0, 1000) }
        for (i in joltage.indices) {
            val terms = mutableListOf<IntVar>()
            for (j in buttonsBitmask.indices) {
                if (i in buttons[j]) terms.add(vars[j])
            }
            model.sum(terms.toTypedArray(), "=", joltage[i]).post()
        }
        model.sum(vars.toTypedArray(), "=", model.intVar("sum", 0, 10000)).post()
        val solver = model.solver
        var minSum: Long = Long.MAX_VALUE
        while (solver.solve()) {
            minSum = min(minSum, vars.sumOf { it.value.toLong() })
        }

        sum += minSum
    }

    return sum
}

fun main() {
    val input = readInput("y2025/Day10")
    // 409
    part1(input).println()
    // 15489
    part2(input).println()
}
