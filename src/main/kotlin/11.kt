import util.Day
import util.Input
import util.containers.Grid
import java.lang.IllegalStateException

class Day11 : Day {
    override val day = 11

    override fun part1() {
        val input = Input.readAs2DIntList(day)
        val grid = Grid(input)

        val result = (0..99).fold(0) { flashCount, i ->
            //First increase all values
            grid.forEach {
                it.value++
            }

            //Second let the fields emit
            while (grid.filter { it.value > 9 && !it.flag }.count() > 0) {
                val toEmit = grid
                    .filter { it.value > 9 && !it.flag }
                toEmit.forEach { it.flag = true }
                toEmit
                    .flatMap(grid::getNeighbors)
                    .forEach {
                        it.value++
                    }
            }

            //Last, set emitting values to 0
            val emitting = grid.filter { it.flag }
            val emittingCount = emitting.count()

            emitting.forEach {
                it.flag = false
                it.value = 0
            }

            flashCount + emittingCount
        }

        return println("Day $day-1: $result")
    }


    override fun part2() {
        val input = Input.readAs2DIntList(day)
        val grid = Grid(input)

        var result = 0
        var allEmitting = false
        while (!allEmitting) {
            result++
            //First increase all values
            grid.forEach {
                it.value++
            }

            //Second let the fields emit
            while (grid.filter { it.value > 9 && !it.flag }.count() > 0) {
                val toEmit = grid
                    .filter { it.value > 9 && !it.flag }
                toEmit.forEach { it.flag = true }
                toEmit
                    .flatMap(grid::getNeighbors)
                    .forEach {
                        it.value++
                    }
            }

            allEmitting = grid.all { it.flag }

            //Last, set emitting values to 0
            grid.filter { it.flag }
                .forEach {
                    it.flag = false
                    it.value = 0
                }

        }
        return println("Day $day-2: $result")
    }

}