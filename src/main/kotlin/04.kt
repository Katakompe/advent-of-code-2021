import util.Day
import util.Input

class Day4 : Day {
    override val day = 4


    data class BingoField(
        val data: List<List<Int>>,
        val rows: List<FieldSeq> = toRows(data),
        val cols: List<FieldSeq> = toCols(data)
    ) {


        fun checkNumber(i: Int): BingoField {
            val newRows = rows.map { it.checkNumber(i) }
            val newCols = cols.map { it.checkNumber(i) }
            return this.copy(data, newRows, newCols)
        }


        fun hasWon(): Boolean {
            return (rows + cols).any { it.fields.all { it.checked } }
        }

        fun unmarked(): List<Int> {
            return rows.flatMap { it.fields }
                .filter { !it.checked }
                .map { it.value }

        }


        companion object {
            fun toRows(data: List<List<Int>>): List<FieldSeq> {
                return data.map {
                    FieldSeq(it)
                }
            }


            fun toCols(data: List<List<Int>>): List<FieldSeq> {
                return List(data.size) { ix ->
                    FieldSeq(data.map { it.get(ix) })
                }
            }
        }

    }


    data class FieldSeq(
        val lineData: List<Int>,
        val fields: List<Field> = toFields(lineData)
    ) {


        fun checkNumber(i: Int): FieldSeq {
            val fieldSeq: List<Field> = fields.map {
                if (!it.checked && it.value == i) {
                    it.withChecked()
                } else {
                    it
                }
            }
            return this.copy(lineData, fieldSeq)
        }

        companion object {
            fun toFields(lineData: List<Int>): List<Field> {
                return lineData.map { Field(it) }
            }
        }
    }


    data class Field(
        val value: Int,
        val checked: Boolean = false
    ) {


        fun withChecked(): Field {
            return this.copy(checked = true)
        }
    }

    fun readBingoFields(input: List<String>): List<BingoField> {
        return input.subList(2, input.size)
            .chunked(6)
            .map { it.subList(0, 5) }
            .map {
                it.map {
                    it.trim().replace("  ", " ").split(" ")
                        .map { it.toInt() }
                }
            }
            .map { BingoField(it) }


    }


    override fun part1() {
        val input = Input.readAsString(day)
        val numbers: List<Int> = input.get(0).split(",").map { it.toInt() }
        val bingoFields = readBingoFields(input)


        val winningTurn = numbers.runningFold(bingoFields) { fields, num ->
            fields.map {
                it.checkNumber(num)
            }
        }
            .mapIndexed { index, list -> Pair(index, list) }
            .filter { it.second.any { it.hasWon() } }
            .first()

        val lastNum = numbers[winningTurn.first - 1]
        val winningField = winningTurn.second.find { it.hasWon() }!!

        val result = winningField.unmarked().sum() * lastNum


        return println("Day $day-1: $result")

    }

    override fun part2() {
        val input = Input.readAsString(day)
        val numbers: List<Int> = input.get(0).split(",").map { it.toInt() }
        val bingoFields = readBingoFields(input)


        val allTurns = numbers.runningFold(bingoFields) { fields, num ->
            fields.map {
                it.checkNumber(num)
            }
        }
            .mapIndexed { index, list -> Pair(index, list) }

        val winningTurns = allTurns.filter { it.second.any { it.hasWon() } }


        val lastWinningTurn = allTurns.foldIndexed(listOf<Int>()) { i, initial, pair ->
            val winners = pair.second.filter { it.hasWon() }
            if (winners.size == winningTurns.last().second.size) {
                initial+ listOf(i)
            } else
                initial
        }
        val winningTurn = lastWinningTurn[0]
        val lastNum = numbers[winningTurn-1]

        val alreadyWonFields = allTurns[winningTurn - 1].second
            .mapIndexed { index, bingoField -> Pair(index, bingoField) }
            .filter { it.second.hasWon() }
            .map { it.first }



        val winningField = allTurns[winningTurn].second
            .filterIndexed { ix, field -> !alreadyWonFields.contains(ix) }
            .filter { it.hasWon() }
          //  .first()

        val result = winningField.first().unmarked().sum() * lastNum


        return println("Day $day-2: $result")

    }

}

