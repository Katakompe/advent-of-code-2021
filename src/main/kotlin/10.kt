import util.Day
import util.Input
import java.lang.IllegalStateException

class Day10 : Day {
    override val day = 10

    val braceMatchings = mapOf(
        '{' to '}',
        '(' to ')',
        '<' to '>',
        '[' to ']'
    )
    val openingBraces = setOf<Char>('[', '(', '<', '{')

    //corrupted lines can be incomplete
    override fun part1() {
        val input = Input.readAsString(day)
        val result = input
            .map(this::errorValue)
            .sum()
        return println("Day $day-1: $result")
    }

    private fun errorValue(line: String): Int {
        val unclosed = ArrayDeque<Char>()

        val errorScores = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        for (brace in line) {
            if (openingBraces.contains(brace)) {
                unclosed.add(brace)
            } else {
                if (braceMatchings.getValue(unclosed.last()).equals(brace)) {
                    unclosed.removeLast()
                } else {
                    return errorScores.getValue(brace)
                }
            }
        }
        return 0
    }

    override fun part2() {
        val input = Input.readAsString(day)
        val resultList = input
            .filter { this.errorValue(it) == 0 }
            .map { this.completionValue(it) }
            .sorted()

        val result = resultList[resultList.size/2]
        return println("Day $day-2: $result")
    }

    private fun completionValue(line: String): Long {
        val unclosed = ArrayDeque<Char>()

        val completionScores = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )

        for (brace in line) {
            if (openingBraces.contains(brace)) {
                unclosed.add(brace)
            } else {
                if (braceMatchings.getValue(unclosed.last()).equals(brace)) {
                    unclosed.removeLast()
                } else {
                    throw IllegalStateException("Found an error in the matching which should have been filtered out.")
                }
            }
        }
        return unclosed.reversed().fold(0L) { acc, c -> acc * 5L + completionScores.getValue(c).toLong() }
    }
}