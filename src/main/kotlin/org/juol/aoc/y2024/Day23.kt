package org.juol.aoc.y2024

import org.jgrapht.Graph
import org.jgrapht.alg.clique.PivotBronKerboschCliqueFinder
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.juol.aoc.utils.*

private fun parse(input: String): Graph<String, DefaultEdge> =
    SimpleGraph<String, DefaultEdge>(DefaultEdge::class.java).also {
        input
            .lines()
            .map { it.split("-") }
            .forEach { (a, b) ->
                it.addVertex(a)
                it.addVertex(b)
                it.addEdge(a, b)
            }
    }

private fun part1(input: String): Int {
    val graph = parse(input)
    val cliques =
        PivotBronKerboschCliqueFinder(graph)
            .asSequence()
            .filter { it.size >= 3 }
            .map { combinations(it.toList(), 3) }
            .flatten()
            .filter { it.any { c -> c.startsWith("t") } }
            .map { it.sorted() }
            .toSet()
    return cliques.size
}

private fun part2(input: String): String {
    val graph = parse(input)
    val p = PivotBronKerboschCliqueFinder(graph)
    val a =
        p.maxBy { it.size }.sorted()

    return a.joinToString(",")
}

fun main() {
    val input = readInput("Day23")
    // 1599
    part1(input).println()
    // av,ax,dg,di,dw,fa,ge,kh,ki,ot,qw,vz,yw
    part2(input).println()
}
