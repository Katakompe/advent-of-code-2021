import util.Day
import util.Input
import util.containers.CaveState
import util.containers.CaveState2
import util.containers.Grid
import java.lang.IllegalStateException
import kotlin.random.Random

class Day12 : Day {
    override val day = 12

    override fun part1() {
        //INPUT PARSING
        val input = Input.readAsString(day)
        val pathMappings = input.map {
            val splitter = it.split("-")
            splitter.first() to splitter.last()
        }
        val aToB = pathMappings.groupBy { it.first }
            .mapValues { it.value.map { it.second } }
        val bToA = pathMappings.groupBy { it.second }
            .mapValues { it.value.map { it.first } }

        val caveStructure = (aToB.keys + bToA.keys)
            .associateWith { listOf(aToB[it], bToA[it]).flatMap { v -> v ?: listOf<String>() } }


        //PATHFINDING LOOP
        var currentState = listOf("start" to CaveState(caveStructure))
        var unexploredPathsExist = true

        while (unexploredPathsExist) {
            currentState = currentState.flatMap { it.second.visitNeighbors(it.first) }
            unexploredPathsExist = currentState.any { it.second.getPaths(it.first).size > 0 }
        }

        //RESULT COUNTING
        val result = currentState.map { it.second.caves.getValue("end").visitations }
            .sum()

        return println("Day $day-1: $result")
    }


    override fun part2() {
        //INPUT PARSING
        val input = Input.readAsString(day)
        val pathMappings = input.map {
            val splitter = it.split("-")
            splitter.first() to splitter.last()
        }
        val aToB = pathMappings.groupBy { it.first }
            .mapValues { it.value.map { it.second } }
        val bToA = pathMappings.groupBy { it.second }
            .mapValues { it.value.map { it.first } }

        val caveStructure = (aToB.keys + bToA.keys)
            .associateWith { listOf(aToB[it], bToA[it]).flatMap { v -> v ?: listOf<String>() } }

        //PATHFINDING LOOP
        var currentState = listOf("start" to CaveState2(caveStructure))
        var unexploredPathsExist = true

        while (unexploredPathsExist) {
            currentState = currentState.flatMap { it.second.visitNeighbors(it.first) }
            unexploredPathsExist = currentState.any { it.second.getPaths(it.first).size > 0 }
        }

        //RESULT COUNTING
        val result = currentState.map { it.second.caves.getValue("end").visitations }
            .sum()

        return println("Day $day-2: $result")
    }

}