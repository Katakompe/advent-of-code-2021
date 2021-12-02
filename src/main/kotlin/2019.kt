import util.Day
import util.Input
import java.lang.Integer.sum
import kotlin.math.absoluteValue

class Day2019 : Day {
    override val day = 2019

    override fun part1() {
        val input = Input.readAsString(2019).map { it.split(",") }
        val wirecords = input.map { wire ->
            var currentPosition = Pair(0, 0)
            wire.map {
                val dir = it[0]
                val len = it.substring(1)
                Pair(dir, len)
            }.map {
                val noOfSteps = it.second.toInt()
                val result = when (it.first) {
                    'R' -> (1..noOfSteps).map { Pair(currentPosition.first + it, currentPosition.second) }
                    'L' -> (1..noOfSteps).map { Pair(currentPosition.first - it, currentPosition.second) }
                    'U' -> (1..noOfSteps).map { Pair(currentPosition.first, currentPosition.second + it) }
                    'D' -> (1..noOfSteps).map { Pair(currentPosition.first, currentPosition.second - it) }
                    else -> listOf()
                }
                currentPosition = result.last()
                result
            }.flatten()

        }
        val first = wirecords[0]
        val second = wirecords[1]

        val result = first.intersect(second).map { it.first.absoluteValue + it.second.absoluteValue }.minOrNull()

        return println("Day $day-1: $result")

    }

    override fun part2() {
        val input = Input.readAsString(2019).map { it.split(",") }
        val wirecords = input.map { wire ->
            var currentPosition = Pair(0, 0)
            wire.map {
                val dir = it[0]
                val len = it.substring(1)
                Pair(dir, len)
            }.map {
                val noOfSteps = it.second.toInt()
                val result = when (it.first) {
                    'R' -> (1..noOfSteps).map { Pair(currentPosition.first + it, currentPosition.second) }
                    'L' -> (1..noOfSteps).map { Pair(currentPosition.first - it, currentPosition.second) }
                    'U' -> (1..noOfSteps).map { Pair(currentPosition.first, currentPosition.second + it) }
                    'D' -> (1..noOfSteps).map { Pair(currentPosition.first, currentPosition.second - it) }
                    else -> listOf()
                }
                currentPosition = result.last()
                result
            }.flatten()

        }
        val first = wirecords[0]
        val second = wirecords[1]

        val result = first.intersect(second).map { first.indexOf(it) + second.indexOf(it) + 2 }.minOrNull()

        return println("Day $day-1: $result")
    }
}
