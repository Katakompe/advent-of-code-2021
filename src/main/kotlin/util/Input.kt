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

        fun readAs2DIntList(day: Int): List<List<Int>> =
            File(resourcePath + "input-" + day + ".txt").useLines {
                it.toList().map { value ->
                    value.toList().map { it.digitToInt() }
                }

            }

    }
}