import util.Day
import util.Input

class Day3 : Day {
    override val day = 3

    @OptIn(ExperimentalStdlibApi::class)
    override fun part1() {
        val input = Input.readAsString(3)
        val oneCount = input.map { it.toList().map {a -> a.digitToInt()} }
            .reduce { a, b -> a.zip(b).map { it.first + it.second } }
        val zeroCount = oneCount.map { input.size - it  }

        val mostSignificantNumber = oneCount.zip(zeroCount).map { (ones, zeros) -> if (ones > zeros) 1 else 0 }
        val leastSignificantNumber = mostSignificantNumber.map { if (it == 1) 0 else 1 }
        val result = mostSignificantNumber.map(Int::toString).reduce{ a,b -> a+b }.toInt(2) *
                leastSignificantNumber.map(Int::toString).reduce{ a,b -> a+b }.toInt(2)

        return println("Day $day-1: $result")

    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun part2() {
        val input = Input.readAsString(3)
        val numDigits = input[0].length
        var remainingMostSig = input
        for (i in 0..numDigits) {
            val oneCount = remainingMostSig.map { it.toList().map { a -> a.digitToInt() } }
                .reduce { a, b -> a.zip(b).map { it.first + it.second } }
            val zeroCount = oneCount.map { remainingMostSig.size - it }

            val mostSignificantNumber = oneCount.zip(zeroCount).map { (ones, zeros) -> if (ones >= zeros) 1 else 0 }

            remainingMostSig = remainingMostSig.filter { it[i].digitToInt() == mostSignificantNumber[i] }
            if (remainingMostSig.size==1) {
                break
            }
        }

        var remainingLeastSig = input
        for (i in 0..numDigits) {
            val oneCount = remainingLeastSig.map { it.toList().map { a -> a.digitToInt() } }
                .reduce { a, b -> a.zip(b).map { it.first + it.second } }
            val zeroCount = oneCount.map { remainingLeastSig.size - it }

            val leastSignificantNumber = oneCount.zip(zeroCount).map { (ones, zeros) -> if (ones >= zeros) 0 else 1 }

            remainingLeastSig = remainingLeastSig.filter { it[i].digitToInt() == leastSignificantNumber[i] }
            if (remainingLeastSig.size==1) {
                break
            }
        }

        val result1 = remainingMostSig[0].toInt(2)
        val result2 = remainingLeastSig[0].toInt(2)
        val result = result1 * result2
        return println("Day $day-2: $result")
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun findCommonValues(input: List<String>, commonNumbers: List<Int>): Int {
        var filteredOutput = input
        for(i in 0..commonNumbers.size) {
            filteredOutput = filteredOutput.filter { it[i].digitToInt() == commonNumbers[i] }
            if (filteredOutput.size==1) {
                break
            }
        }
        return filteredOutput[0].toInt(2)
    }
}

//wrong: 2572440
//       602176