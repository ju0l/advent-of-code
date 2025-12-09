package org.juol.aoc.y2025

import org.juol.aoc.utils.*
import kotlin.math.sqrt

private data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    override fun toString(): String = "($x,$y,$z)"
}

private fun euclideanDistance(
    p1: Point3D,
    p2: Point3D,
): Double {
    val dx = (p1.x - p2.x).toDouble()
    val dy = (p1.y - p2.y).toDouble()
    val dz = (p1.z - p2.z).toDouble()
    return sqrt(dx * dx + dy * dy + dz * dz)
}

private data class Edge3D(
    val p1: Point3D,
    val p2: Point3D,
    val distance: Double,
)

private fun String.parsePoints3D(): List<Point3D> =
    toIntGrid(separator = ",")
        .rows()
        .map { (x, y, z) -> Point3D(x, y, z) }

private fun createDistanceMap(points: List<Point3D>): List<Edge3D> {
    val distanceMap = mutableListOf<Edge3D>()
    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val p1 = points[i]
            val p2 = points[j]
            val dst = euclideanDistance(p1, p2)
            distanceMap.add(Edge3D(p1, p2, dst))
        }
    }
    distanceMap.sortBy { it.distance }

    return distanceMap
}

private fun part1(input: String): Long {
    val points = input.parsePoints3D()
    val distanceMap = createDistanceMap(points)

    val groups: MutableList<MutableList<Point3D>> = mutableListOf()
    distanceMap.take(1000).forEach { (p1, p2, _) ->
        val g1 = groups.find { it.contains(p1) }
        val g2 = groups.find { it.contains(p2) }

        when {
            g1 != null && g2 != null -> {
                if (g1 != g2) {
                    // Merge groups
                    g1.addAll(g2)
                    groups.remove(g2)
                }
            }

            g1 != null -> {
                g1.add(p2)
            }

            g2 != null -> {
                g2.add(p1)
            }

            else -> {
                groups.add(mutableListOf(p1, p2))
            }
        }
    }

    groups.sortByDescending { it.size }
    var c = 1L
    groups.take(3).forEach { c *= it.size }
    return c
}

private fun part2(input: String): Long {
    val points = input.parsePoints3D()
    val distanceMap = createDistanceMap(points)

    val groups: MutableList<MutableList<Point3D>> = mutableListOf()

    for ((p1, p2) in distanceMap) {
        val g1 = groups.find { it.contains(p1) }
        val g2 = groups.find { it.contains(p2) }

        when {
            g1 != null && g2 != null -> {
                if (g1 != g2) {
                    // Merge groups
                    g1.addAll(g2)
                    groups.remove(g2)
                }
            }

            g1 != null -> {
                g1.add(p2)
            }

            g2 != null -> {
                g2.add(p1)
            }

            else -> {
                groups.add(mutableListOf(p1, p2))
            }
        }
        if (groups.size == 1 && groups[0].size == points.size) {
            return p2.x.toLong() * p1.x.toLong()
        }
    }

    return 0
}

fun main() {
    val input = readInput("y2025/Day08")
    // 57970
    part1(input).println()
    // 8520040659
    part2(input).println()
}
