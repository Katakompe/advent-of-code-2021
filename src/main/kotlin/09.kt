import util.Day
import util.Input
import kotlin.math.abs

class Day9 : Day {
    override val day = 9


    override fun part1() {
        val input = Input.readAs2DIntList(day)


        val result = input.mapIndexed { i, row -> row.mapIndexed { j, field -> field to neighbors(i, j, input) } }
            .flatten()
            .filter { it.second.all { v -> it.first < v } }
            .map { it.first + 1 }
            .sum()

        return println("Day $day-1: $result")
    }

    private fun neighbors(i: Int, j: Int, input: List<List<Int>>): List<Int> {
        return listOf(
            i - 1 to j,
            i + 1 to j,
            i to j - 1,
            i to j + 1
        )
            .filter { it.first >= 0 && it.first < input.size && it.second >= 0 && it.second < input[0].size }
            .map { input[it.first][it.second] }

    }


    data class Field(val i: Int, val j: Int, val height:Int, var basinId: Int = -1, var visited: Boolean = false) {
        fun visit() {
            this.visited = true
        }

        fun assignBasin(id: Int) {
            this.basinId = id
        }

        companion object {
            var idCounter = 0
        }
    }

    override fun part2() {
        val input = Input.readAs2DIntList(day)
        val fields = input.mapIndexed { i, row -> row.mapIndexed { j, field -> Field(i, j, field) } }
        fields.forEach { row -> row.forEach{
                field -> flood(field, fields)
                Field.idCounter++
            }
        }

        val result =  fields.flatten()
            .filter { it.basinId != -1}
            .groupBy { it.basinId }
            .toList()
            .sortedBy { it.second.size }
            .reversed()
            .subList(0,3)
            .map { it.second.size }
            .fold(1) {v, total -> v*total}

        return println("Day $day-2: $result")
    }


    fun flood(field: Field, fields: List<List<Field>>) {
        if(field.height==9 || field.visited) {
            field.visit()
            return
        }
        field.assignBasin(Field.idCounter)
        field.visit()

        val i = field.i
        val j = field.j

        listOf(
            i - 1 to j,
            i + 1 to j,
            i to j - 1,
            i to j + 1
        )
            .filter { it.first >= 0 && it.first < fields.size && it.second >= 0 && it.second < fields[0].size }
            .forEach { flood(fields[it.first][it.second], fields) }

    }
}

