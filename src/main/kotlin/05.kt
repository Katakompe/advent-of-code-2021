import util.Day
import util.Input

class Day5 : Day {
    override val day = 5

    data class Point(
        val x: Int,
        val y: Int
    ) {
        companion object {
            fun fromList(l: List<Int>): Point {
                assert(l.size == 2)
                return Point(l.first(), l.last())
            }

            fun fromPair(p: Pair<Int, Int>): Point {
                return Point(p.first, p.second)
            }
        }
    }

    override fun part1() {
        val input = Input.readAsString(day)
        val pointPairs = input.map {
            it.split(" -> ")
                .map {
                    it.split(",")
                        .map { it.toInt() }
                }
                .map { Point.fromList(it) }
        }
            .map { Pair(it.first(), it.last()) }

        val verticals = pointPairs.filter { pair -> pair.first.x == pair.second.x }
            .flatMap { pair ->
                if (pair.first.y < pair.second.y) {
                    (pair.first.y..pair.second.y).map { Point(pair.first.x, it) }
                } else {
                    (pair.second.y..pair.first.y).map { Point(pair.first.x, it) }
                }
            }
        val horizontals = pointPairs.filter { pair -> pair.first.y == pair.second.y }
            .flatMap { pair ->
                if (pair.first.x < pair.second.x) {
                    (pair.first.x .. pair.second.x).map { Point( it, pair.first.y) }
                } else {
                    (pair.second.x .. pair.first.x).map { Point( it, pair.first.y) } }
                }

        val result = (horizontals+verticals).groupBy { it }
            .filter { it.value.size > 1}
            .count()

        return println("Day $day-1: $result")

    }

    override fun part2() {
        val input = Input.readAsString(day)
        val pointPairs = input.map {
            it.split(" -> ")
                .map {
                    it.split(",")
                        .map { it.toInt() }
                }
                .map { Point.fromList(it) }
        }
            .map { Pair(it.first(), it.last()) }

        val verticals = pointPairs.filter { pair -> pair.first.x == pair.second.x }
            .flatMap { pair ->
                if (pair.first.y < pair.second.y) {
                    (pair.first.y..pair.second.y).map { Point(pair.first.x, it) }
                } else {
                    (pair.second.y..pair.first.y).map { Point(pair.first.x, it) }
                }
            }
        val horizontals = pointPairs.filter { pair -> pair.first.y == pair.second.y }
            .flatMap { pair ->
                if (pair.first.x < pair.second.x) {
                    (pair.first.x .. pair.second.x).map { Point( it, pair.first.y) }
                } else {
                    (pair.second.x .. pair.first.x).map { Point( it, pair.first.y) } }
            }


        val diagonals = pointPairs.filter {pair -> pair.first.x != pair.second.x && pair.first.y != pair.second.y}
            .flatMap { pair ->
                val x1 = pair.first.x
                val x2 = pair.second.x
                val y1 = pair.first.y
                val y2 = pair.second.y

                val xrange = if (x1<x2) (x1..x2) else (x2..x1).reversed()
                val yrange = if (y1<y2) (y1..y2) else (y2..y1).reversed()
                xrange.zip(yrange).map { xyPair -> Point.fromPair(xyPair) }
            }

        val result = (horizontals+verticals+diagonals).groupBy { it }
            .filter { it.value.size > 1}
            .count()


        return println("Day $day-2: $result")

    }

}

