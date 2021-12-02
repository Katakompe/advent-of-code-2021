import util.Day
import util.Input

class Day1: Day {
    override val day = 1

    override fun part1() {
        val input = Input.readAsInt(1)
        val result = input.zipWithNext { a, b ->
            if (a < b) 1 else 0
        }.sum()
        return println("Day $day-1: $result")

    }

    override fun part2() {
        val input = Input.readAsInt(1)
        val result = input
            .windowed(3)
            .map {it.sum()}
            .zipWithNext {a,b ->
                if (a<b) 1 else 0
            }.sum()

        return println("Day $day-2: $result")
    }
}
