import util.Day
import util.Input
import util.containers.Field
import util.containers.Grid
import kotlin.math.abs

class Day15 : Day {
    override val day = 15

    data class Chiton(
        val risk: Int,
        var visited: Boolean = false,
        var minCostToHere: Int = Int.MAX_VALUE,
        var predecessor: Field<Chiton>? = null
    ) {
        fun visit(pathCost: Int) {
            this.visited = true
            this.minCostToHere = pathCost
        }
    }

    override fun part1() {
        val input: List<List<Chiton>> = Input.readAs2DIntList(day)
            .map { it.map { risk -> Chiton(risk) } }
        val cavern = Grid(input)

        cavern.grid[0][0].value.visit(0)
        val openFields = mutableSetOf(cavern.grid[0][0])
        val closedFields = mutableSetOf<Field<Chiton>>()
        val end = cavern.rowLength - 1 to cavern.colLength - 1
        var resultNode: Field<Chiton>? = null

        do {
            val nextStep = openFields.minByOrNull { it.value.minCostToHere + manhattanDist(it.x to it.y, end) }!!
            openFields.remove(nextStep)
            if (nextStep.x == end.first && nextStep.y == end.second) {
                resultNode = nextStep
            }
            closedFields.add(nextStep)

            cavern.getNeighbors(nextStep, diagonals = false).forEach {
                if (!closedFields.contains(it)) {
                    val newMinCost = nextStep.value.minCostToHere + it.value.risk
                    if (openFields.contains(it) && newMinCost >= it.value.minCostToHere) {
                        return@forEach
                    }
                    it.value.predecessor = nextStep
                    it.value.visit(newMinCost)
                    openFields.add(it)
                }
            }


        } while (!openFields.isEmpty())


        if (resultNode != null) {
            val result = resultNode.value.minCostToHere
            println("Day $day-1: $result")

        } else {
            println("Day $day-1: No result")

        }

    }

    private fun manhattanDist(start: Pair<Int, Int>, end: Pair<Int, Int>): Int =
        abs(end.first - start.first) + abs(end.second - start.second)


    //expands the grid and adds 1 for each expansion step in a dimension wrapping it at 9 back to 1
    fun expand(grid: Grid<Chiton>, i: Int): Grid<Chiton> {
        val oldRowSize = grid.rowLength
        val oldColSize = grid.colLength
        val newGrids = (0..8).associate {
            it to grid.grid.map { row ->
                row.map { field ->
                    val newRisk = ((field.value.risk - 1 + it) % 9) + 1
                    field.copy(value = field.value.copy(risk = newRisk))
                }
            }
        }

        var newGrid: List<List<Field<Chiton>>> = mutableListOf()
        for (j in 0..4) {
            newGrid = newGrid + newGrids.getValue(j).map { it.map { field-> field.copy(x =field.x+(oldRowSize*j)) } }
        }

        val resultGrid = newGrid.map { row ->
            val newRows = row.toMutableList()
            for (k in 1..4) {
                val newRow = row.map {
                    val newRisk = ((it.value.risk - 1 + k) % 9) + 1
                    it.copy(y=it.y+(oldColSize*k) ,value = it.value.copy(risk = newRisk))
                }
                newRows.addAll(newRow)
            }
            newRows.toList()
        }
        grid.grid= resultGrid
        grid.colLength= resultGrid.size
        grid.rowLength= resultGrid.get(0).size
        return grid


    }


    override fun part2() {
        val input: List<List<Chiton>> = Input.readAs2DIntList(day)
            .map { it.map { risk -> Chiton(risk) } }
        val cavern = expand(Grid(input), 5)

        cavern.grid[0][0].value.visit(0)
        val openFields = mutableSetOf(cavern.grid[0][0])
        val closedFields = mutableSetOf<Field<Chiton>>()
        val end = cavern.rowLength - 1 to cavern.colLength - 1
        var resultNode: Field<Chiton>? = null

        do {
            val nextStep = openFields.minByOrNull { it.value.minCostToHere + manhattanDist(it.x to it.y, end) }!!
            openFields.remove(nextStep)
            if (nextStep.x == end.first && nextStep.y == end.second) {
                resultNode = nextStep
            }
            closedFields.add(nextStep)

            cavern.getNeighbors(nextStep, diagonals = false).forEach {
                if (!closedFields.contains(it)) {
                    val newMinCost = nextStep.value.minCostToHere + it.value.risk
                    if (openFields.contains(it) && newMinCost >= it.value.minCostToHere) {
                        return@forEach
                    }
                    it.value.predecessor = nextStep
                    it.value.visit(newMinCost)
                    openFields.add(it)
                }
            }


        } while (!openFields.isEmpty())


        if (resultNode != null) {
            val result = resultNode.value.minCostToHere
            return println("Day $day-2: $result")

        } else {
            println("Day $day-2: No result")

        }

    }
}