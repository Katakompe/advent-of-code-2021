import util.Day
import util.Input
import kotlin.math.abs

class Day7 : Day {
    override val day = 7


    override fun part1() {
        val input = Input.readSingleLineAsInt(day)

        val targetPosition = input.sorted()[input.size/2]
        val result = input.map { abs(it-targetPosition) }.sum()

        return println("Day $day-1: $result")
    }

    override fun part2() {
        val input = Input.readSingleLineAsInt(day)

        val targetPosition = input.average().toInt()
        val result = input.map { (0..abs(it-targetPosition)).sum() }.sum()

        return println("Day $day-2: $result")
    }
}

