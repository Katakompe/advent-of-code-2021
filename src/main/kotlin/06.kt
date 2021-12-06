import util.Day
import util.Input
import java.math.BigInteger

class Day6 : Day {
    override val day = 6


    override fun part1() {
        val input = Input.readSingleLineAsInt(day)

        val result = (0..79).fold(input) { fish, _ ->
            val next = fish.flatMap {
                if (it == 0) {
                    listOf(6, 8)
                } else
                    listOf(it - 1)
            }
            next
        }.count()

        return println("Day $day-1: $result")
    }

    override fun part2() {
        val input = Input.readSingleLineAsInt(day)


        val fishMap = input.groupBy { it }.mapValues { it.value.count().toBigInteger() }

        val result = (0..255).fold(fishMap) { fish, _ ->
            val newMap: Map<Int, BigInteger> = fish.mapKeys {
                if(it.key == 0) 6 else it.key - 1
            }
            val sixSum= fish.getOrDefault(0, BigInteger.valueOf(0)) + fish.getOrDefault(7, BigInteger.valueOf(0))
            val newMapWithCorrectSixes = newMap + Pair(6, sixSum)
            newMapWithCorrectSixes + Pair(8, fish.getOrDefault(0, BigInteger.valueOf(0)))

        }
            .map { it.value }
            .reduce{a,b -> a+b}

        return println("Day $day-2: $result")
    }
}

