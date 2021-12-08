import util.Day
import util.Input
import kotlin.math.abs

class Day8 : Day {
    override val day = 8


    override fun part1() {
        val input = Input.readAsString(day)
            .map {
                val split = it.split("|")
                split.first().trim() to split.last().trim()
            }.map {
                it.first.split(" ") to it.second.split(" ")
            }

        val result = input.map {
            countOneFourSevenEights(it)
        }.sum()


        return println("Day $day-1: $result")
    }

    private fun countOneFourSevenEights(line: Pair<List<String>, List<String>>): Int {
        val numbers = line.second

        val numberLengths = numbers.groupBy { it.length }.mapValues { it.value.count() }
        val count = numberLengths.getOrDefault(2, 0) +
                numberLengths.getOrDefault(4, 0) +
                numberLengths.getOrDefault(3, 0) +
                numberLengths.getOrDefault(7, 0)


        return count

    }

    override fun part2() {
        val input = Input.readAsString(day)
            .map {
                val split = it.split("|")
                split.first().trim() to split.last().trim()
            }.map {
                it.first.split(" ") to it.second.split(" ")
            }

        val result = input.map {
            decodeNumber(it)
        }.sum()



        return println("Day $day-2: $result")
    }


    private fun decodeNumber(it: Pair<List<String>, List<String>>): Int {
        val digits = it.first
        val numbers = it.second

        val mappings = mutableMapOf<Char, Char>()
        val numberMappings = mutableMapOf<String, Int>()
        val digitsByLength = digits.groupBy { it.length }

        val seven = digitsByLength.getValue(3).first().toList()
        numberMappings.put(seven.sorted().joinToString(""), 7)
        val one = digitsByLength.getValue(2).first().toList()
        numberMappings.put(one.sorted().joinToString(""), 1)

        mappings.put('a', (seven - one).first())

        val four = digitsByLength.getValue(4).first().toList()
        numberMappings.put(four.sorted().joinToString(""), 4)
        val three = digitsByLength.getValue(5).filter { it.toList().intersect(one).size == 2 }.first().toList()
        numberMappings.put(three.sorted().joinToString(""), 3)

        mappings.put('b', (four - three).first())

        val five = digitsByLength.getValue(5).filter { it.contains(mappings.getValue('b')) }.first().toList()
        numberMappings.put(five.sorted().joinToString(""), 5)

        val two = digitsByLength.getValue(5)
            .filter { !it.contains(mappings.getValue('b')) && !it.equals(three.joinToString("")) }.first().toList()
        numberMappings.put(two.sorted().joinToString(""), 2)

        val eight = digitsByLength.getValue(7).first().toList()
        numberMappings.put(eight.sorted().joinToString(""), 8)

        mappings.put('c', (one - five).first())

        val six = digitsByLength.getValue(6).filter { !it.contains(mappings.getValue('c')) }.first().toList()
        numberMappings.put(six.sorted().joinToString(""), 6)

        mappings.put('e', (six - five).first())

        val nine = digitsByLength.getValue(6).filter { !it.contains(mappings.getValue('e')) }.first().toList()
        numberMappings.put(nine.sorted().joinToString(""), 9)

        val zero =
            digitsByLength.getValue(6).filter { !it.equals(nine.joinToString("")) && !it.equals(six.joinToString("")) }.first().toList()
        numberMappings.put(zero.sorted().joinToString(""), 0)

        assert(numberMappings.size == 10)

        return numbers.zip(listOf(1000, 100, 10, 1)).map { pair ->

            val n = numberMappings.get(pair.first.toList().sorted().joinToString(""))
            n!! * pair.second
        }.sum()

    }
}

