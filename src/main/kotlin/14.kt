import util.Day
import util.Input
import util.containers.CaveState
import util.containers.CaveState2
import util.containers.Grid
import java.lang.IllegalStateException
import kotlin.random.Random

class Day14 : Day {
    override val day = 14

    override fun part1() {
        val (input, rawRules) = Input.readAs2EmptyLineSplitInputs(day,
            { s -> s },
            { s ->
                val split = s.split(" -> ")
                split.first() to split.last()
            }
        )
        val rules = rawRules.groupBy { it.first }.mapValues { it.value.first().second }
        val startingConfig: List<Char> = input.first().toList()
        val charCounts = (0..9).fold(startingConfig) { i, _ ->
            listOf(i[0]) + i.zipWithNext().flatMap {
                val letters = listOf<Char>(it.first, it.second).joinToString("")
                (rules.getValue(letters) + letters[1]).toList()
            }
        }.groupBy { it }
            .map { it.value.size }
        val result = charCounts.maxOrNull()!! - charCounts.minOrNull()!!
        return println("Day $day-1: $result")
    }

    override fun part2() {
        val (input, rawRules) = Input.readAs2EmptyLineSplitInputs(day,
            { s -> s },
            { s ->
                val split = s.split(" -> ")
                split.first() to split.last()
            }
        )
        val rules = rawRules.groupBy { it.first.first() to it.first.last() }.mapValues { it.value.first().second }
        val startingConfig: Map<Pair<Char, Char>, Long> = input.first().zipWithNext().groupBy { it }
            .mapValues { it.value.size.toLong() }

        var letterCounts: Map<Char, Long> = input.first().groupBy { it }.mapValues { it.value.size.toLong() }

        (0..39).fold(startingConfig) { config, _ ->
            val newCombinations = config.filterValues { it > 0 }.flatMap {
                val newLetter = rules.getValue(it.key)[0]
                letterCounts =
                    letterCounts + (newLetter to letterCounts.getOrDefault(newLetter, 0L) + it.value.toLong())
                listOf(
                    it.value to (it.key.first to newLetter),
                    it.value to (newLetter to it.key.second)
                )
            }
            newCombinations.groupBy { it.second }.mapValues { it.value.map { v -> v.first }.sum() }
        }

        val result = letterCounts.values.maxOrNull()!! - letterCounts.values.minOrNull()!!
        return println("Day $day-2: $result")
    }
}