package org.juol.aoc.y2025

import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleDirectedGraph
import org.juol.aoc.utils.*

private fun parseGraph(input: String): SimpleDirectedGraph<String, DefaultEdge> =
    SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge::class.java).also { g ->
        input
            .lineSequence()
            .map { it.split(" ") }
            .filter { it.isNotEmpty() }
            .forEach { words ->
                val from = words.first().trimEnd(':')
                val to = words.subList(1, words.size)
                g.addVertex(from)
                to.forEach { v ->
                    g.addVertex(v)
                    g.addEdge(from, v)
                }
            }
    }

private fun part1(input: String): Int {
    val graph = parseGraph(input)
    val dp = AllDirectedPaths(graph)
    val paths = dp.getAllPaths("you", "out", true, null)

    return paths.size
}

private fun part2(input: String): Long {
    val graph = parseGraph(input)
    val dp = AllDirectedPaths(graph)
    val a =
        dp.getAllPaths("svr", "fft", true, null).size.toLong()
    val b =
        dp.getAllPaths("fft", "dac", true, null).size.toLong()
    val c =
        dp.getAllPaths("dac", "out", true, null).size.toLong()

    return a * b * c
}

fun main() {
    val input = readInput("y2025/Day11")
    // 643
    part1(input).println()
    // 417190406827152
    part2(input).println()
}
