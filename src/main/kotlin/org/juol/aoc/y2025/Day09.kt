package org.juol.aoc.y2025

import org.juol.aoc.utils.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import kotlin.collections.toTypedArray
import kotlin.math.abs
import kotlin.math.max

private fun part1(input: String): Long {
    val corners =
        input.lines().map {
            val (x, y) = it.split(",").map(String::toDouble)
            Coordinate(x, y)
        }

    var largestArea = 0L

    for (i in corners.indices) {
        for (j in i + 1 until corners.size) {
            val p1 = corners[i]
            val p2 = corners[j]
            val area = ((abs(p1.x - p2.x) + 1) * (abs(p1.y - p2.y) + 1)).toLong()
            largestArea = max(largestArea, area)
        }
    }

    return largestArea
}

private fun part2(input: String): Long {
    val factory = GeometryFactory()
    val corners =
        input
            .lines()
            .map {
                val (x, y) = it.split(",").map(String::toDouble)
                Coordinate(x, y)
            }.toMutableList()
    corners.add(corners.first())

    val polygon = factory.createPolygon(corners.toTypedArray())

    var largestContainedArea = 0L

    for (i in corners.indices) {
        for (j in i + 1 until corners.size) {
            val p1 = corners[i]
            val p2 = corners[j]
            val corners = arrayOf(p1, Coordinate(p1.x, p2.y), p2, Coordinate(p2.x, p1.y), p1)
            val rect = factory.createPolygon(corners)
            if (polygon.contains(rect)) {
                val area = (abs(p1.x - p2.x) + 1).toLong() * (abs(p1.y - p2.y) + 1).toLong()
                largestContainedArea = max(largestContainedArea, area)
            }
        }
    }

    return largestContainedArea
}

fun main() {
    val input = readInput("y2025/Day09")
    // 4743645488
    part1(input).println()
    // 1529011204
    part2(input).println()
}
