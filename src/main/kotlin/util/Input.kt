package util

import java.io.File

class Input {
    companion object {
        val resourcePath = ".\\src\\main\\resources\\"

        fun readAsInt(day: Int): List<Int> = File(resourcePath + "input-" + day + ".txt").useLines {
            it.toList().map { value ->
                value.toInt()
            }
        }

        fun readAsString(day: Int): List<String> =
            File(resourcePath + "input-" + day + ".txt").useLines {
                it.toList()
            }

        fun readSingleLineAsInt(day: Int): List<Int> {
            val lines = File(resourcePath + "input-" + day + ".txt").readLines()
            assert(lines.size == 1)
            return lines[0].split(",").map { it.toInt() }
        }

        fun readSingleLineAsString(day: Int): String {
            val lines = File(resourcePath + "input-" + day + ".txt").readLines()
            assert(lines.size == 1)
            return lines[0]
        }

        fun readAs2DIntList(day: Int): List<List<Int>> =
            File(resourcePath + "input-" + day + ".txt").useLines {
                it.toList().map { value ->
                    value.toList().map { it.digitToInt() }
                }

            }

        fun <T, U> readAs2EmptyLineSplitInputs(day: Int, f1: (String) -> T, f2: (String) -> U): Pair<List<T>, List<U>> {
            val lines = File(resourcePath + "input-" + day + ".txt").readLines()
            val splitLine = lines.indexOfFirst { it.isBlank() }
            val firstList = lines.subList(0, splitLine).map(f1)
            val secondList = lines.subList(splitLine+1, lines.lastIndex+1).map(f2)

            return firstList to secondList
        }

    }
}