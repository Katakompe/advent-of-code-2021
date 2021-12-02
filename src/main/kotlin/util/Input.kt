package util

import java.io.File

class Input {
    companion object {
        val resourcePath = ".\\src\\main\\resources\\"

        fun readAsInt(day: Int): List<Int> = File(resourcePath+ "input-" + day + ".txt").useLines {
                it.toList().map { value ->
                    value.toInt()
                }
            }

        fun readAsString(day: Int): List<String> =
                File(resourcePath+ "input-" + day + ".txt").useLines {
                    it.toList()
                }

    }
}