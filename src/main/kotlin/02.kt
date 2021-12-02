import util.Day
import util.Input

class Day2 : Day {
    override val day = 2

    override fun part1() {
        val input = Input.readAsString(2)
        val distances = input
            .map {
                val line = it.split(" ")
                val dir = line[0]
                val len: Int = line[1].toInt()
                when (dir) {
                    "forward" -> Pair("x", len)
                    "up" -> Pair("y", -len)
                    "down" -> Pair("y", len)
                    else -> Pair("z", 0)
                }
            }
            .groupBy { it.first }
            .mapValues { it.value.sumBy{ it.second } }
        val horizontal:Int  = distances.getValue("x")
        val vertical:Int = distances.getValue("y")

        val result = vertical*horizontal
        return println("Day $day-1: $result")

    }

    override fun part2() {
        val input = Input.readAsString(2)
        var currentAim = 0
        var depth = 0
        var horiz = 0
        input.map {
                val line = it.split(" ")
                val dir = line[0]
                val len: Int = line[1].toInt()
                when (dir) {
                    "forward" -> {
                        horiz += len
                        depth += len*currentAim
                    }
                    "up" -> currentAim -= len
                    "down" -> currentAim += len
                    else -> Pair("z", 0)
                }
            }
        val result = horiz*depth
        return println("Day $day-2: $result")
    }
}
