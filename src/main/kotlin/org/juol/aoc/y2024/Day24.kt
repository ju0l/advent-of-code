package org.juol.aoc.y2024

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout
import com.mxgraph.layout.mxIGraphLayout
import com.mxgraph.util.mxCellRenderer
import org.jgrapht.ext.JGraphXAdapter
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.DirectedAcyclicGraph
import org.juol.aoc.utils.println
import org.juol.aoc.utils.readInput
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

private data class Gate(
    val a: String,
    val b: String,
    val op: String,
    val out: String?,
) {
    operator fun invoke(
        x: Boolean,
        y: Boolean,
    ): Boolean =
        when (op) {
            "AND" -> x and y
            "OR" -> x or y
            "XOR" -> x xor y
            else -> error("Unknown op: $op")
        }
}

private fun parse(input: String): Pair<Map<String, Boolean>, List<Gate>> {
    val (init, ops) = input.split("\n\n")
    val initMap =
        init
            .lines()
            .associate {
                val (key, value) = it.split(": ")
                key to (value == "1")
            }
    val gateList =
        ops.lines().map {
            val (a, op, b, _, out) = it.split(" ")
            Gate(a, b, op, out)
        }

    return initMap to gateList
}

private fun part1(input: String): Long {
    val (initMap, gates) = parse(input)

    val zOps = gates.filter { it.out?.startsWith("z") == true }

    fun findValue(gate: Gate): Boolean {
        val a = initMap[gate.a] ?: findValue(gates.find { it.out == gate.a }!!)
        val b = initMap[gate.b] ?: findValue(gates.find { it.out == gate.b }!!)

        return gate(a, b)
    }

    val x =
        zOps
            .sortedByDescending { it.out }
            .map { findValue(it) }
            .map { if (it) 1 else 0 }
            .joinToString("")
            .toLong(2)

    return x
}

private fun findOut(
    gates: List<Gate>,
    x: String?,
    op: String,
    y: String?,
): String? =
    gates
        .firstOrNull {
            (it.a == x && it.op == op && it.b == y) ||
                (it.a == y && it.op == op && it.b == x)
        }?.out

private fun swap(
    rules: List<Gate>,
    out1: String?,
    out2: String?,
): List<String?> {
    val newRules =
        rules.map { rule ->
            when (rule.out) {
                out1 -> rule.copy(out = out2)
                out2 -> rule.copy(out = out1)
                else -> rule
            }
        }
    return fix(newRules) + listOf(out1, out2)
}

private fun fix(rules: List<Gate>): List<String?> {
    var cin = findOut(rules, "x00", "AND", "y00")
    for (i in 1 until 45) {
        val x = "x${i.toString().padStart(2, '0')}"
        val y = "y${i.toString().padStart(2, '0')}"
        val z = "z${i.toString().padStart(2, '0')}"

        val xor1 = findOut(rules, x, "XOR", y)
        val and1 = findOut(rules, x, "AND", y)
        val xor2 = findOut(rules, cin, "XOR", xor1)
        val and2 = findOut(rules, cin, "AND", xor1)

        if (xor2 == null && and2 == null) {
            return swap(rules, xor1, and1)
        }

        val carry = findOut(rules, and1, "OR", and2)
        if (xor2 != z) {
            return swap(rules, z, xor2)
        } else {
            cin = carry
        }
    }
    return emptyList()
}

private fun printGraph(gates: List<Gate>) {
    val g =
        DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge::class.java).also {
            val xs =
                gates.filter {
                    it.a.startsWith("x") ||
                        it.b.startsWith("x") ||
                        it.a.startsWith("y") ||
                        it.b.startsWith("y")
                }
            val zs =
                gates.filter {
                    it.a.startsWith("z") ||
                        it.b.startsWith("z")
                }
            val other =
                gates.filter {
                    !(
                        it.a.startsWith("x") ||
                            it.b.startsWith("x") ||
                            it.a.startsWith("y") ||
                            it.b.startsWith("y") ||
                            it.a.startsWith("z") ||
                            it.b.startsWith("z")
                    )
                }

            (xs + other + zs).forEach { rule ->
                val (a, b) = listOf(rule.a, rule.b).sorted()
                it.addVertex(a)
                it.addVertex(b)
                val z = "$a ${rule.op} $b"
                it.addVertex(z)
                it.addVertex(rule.out)
                it.addEdge(a, z)
                it.addEdge(b, z)
                it.addEdge(z, rule.out)
            }
        }

    val graphAdapter = JGraphXAdapter(g)
    val layout: mxIGraphLayout = mxHierarchicalLayout(graphAdapter)
    layout.execute(graphAdapter.getDefaultParent())
    val image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2.0, Color.WHITE, true, null)
    val imgFile = File("src/main/resources/graph.png")
    ImageIO.write(image, "PNG", imgFile)
}

private fun part2(input: String): String {
    val (_, gates) = parse(input)
//    printGraph(gates)
    val swaps = fix(gates)
    val s = swaps.filterNotNull().sorted().joinToString(",")
    return s
}

fun main() {
    val input = readInput("Day24")
    // 50411513338638
    part1(input).println()
    // gfv,hcm,kfs,tqm,vwr,z06,z11,z16
    part2(input).println()
}
