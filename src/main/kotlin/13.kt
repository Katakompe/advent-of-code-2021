import util.Day
import util.Input
import util.containers.CaveState
import util.containers.CaveState2
import util.containers.Grid
import java.lang.IllegalStateException
import kotlin.random.Random

class Day13 : Day {
    override val day = 13

    enum class FOLD_DIRECTION(val direction: String) {
        VERTICAL("x"),
        HORIZONTAL("y");

        companion object {
            fun fromString(dir: String) = if (dir.equals(VERTICAL.direction)) VERTICAL else HORIZONTAL
        }
    }

    class ASCIIArt(val points: List<Pair<Int,Int>>) {
        override fun toString(): String {
            val maxX = points.maxOf { it.first }+1
            val maxY = points.maxOf { it.second }+1
            val artwork = MutableList(maxY) { MutableList(maxX) {'.'} }
            for (p in points) {
                artwork[p.second][p.first] = '#'
            }
            return artwork.map { row-> row.joinToString("") }.joinToString("\n")
        }
    }

    fun fold(points: List<Pair<Int, Int>>, direction: FOLD_DIRECTION, foldLine: Int): List<Pair<Int, Int>> {
        return points.map { p ->
            if (direction== FOLD_DIRECTION.VERTICAL && p.first > foldLine) {
                p.copy(first = (foldLine - (p.first % foldLine)) % foldLine)
            } else if (direction == FOLD_DIRECTION.HORIZONTAL && p.second > foldLine){
                p.copy(second = (foldLine - (p.second % foldLine)) % foldLine)
            } else {
                p
            }
        }.distinct()
    }

    override fun part1() {
        val (points, folds) = Input.readAs2EmptyLineSplitInputs(day,
            { s ->
                val split = s.split(",")
                split.first().toInt() to split.last().toInt()
            },
            { s ->
                val split = s.split(" ").last().split("=")
                FOLD_DIRECTION.fromString(split.first()) to split.last().toInt()
            }
        )

        val firstFold = folds.first()
        val result = this.fold(points, firstFold.first, firstFold.second)
            .count()


        return println("Day $day-1: $result")
    }

    override fun part2() {
        val (points, folds) = Input.readAs2EmptyLineSplitInputs(day,
            { s ->
                val split = s.split(",")
                split.first().toInt() to split.last().toInt()
            },
            { s ->
                val split = s.split(" ").last().split("=")
                FOLD_DIRECTION.fromString(split.first()) to split.last().toInt()
            }
        )

        val result = folds.fold(points) { p, fold ->
            this.fold(p, fold.first,fold.second)
        }
        println(ASCIIArt(result))
        return println("Day $day-2: $result")
    }

}