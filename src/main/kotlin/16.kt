import util.Day
import util.Input
import java.math.BigInteger

inline fun <T> Iterable<T>.takeWhileInclusive(
    predicate: (T) -> Boolean
): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

class Day16 : Day {
    override val day = 16


    data class Package(val hexCode: String, val leadingZeros: Int = 0) {
        val binaryCode: String = hexCode.toBigInteger(radix = 16).toString(2)
            .padStart(hexCode.toBigInteger(radix = 16).toString(2).length + leadingZeros, '0')
        val packetVersion = binaryCode.substring(0, 3).toBigInteger(2)
        val packetTypeId = binaryCode.substring(3, 6).toBigInteger(2)
        val packetLength = parsePacketLength()
        val literalContent = parseLiteralContent()
        val subPackages: List<Package> = parseSubPackages()
        val value: BigInteger = calcValue()

        fun calcValue(): BigInteger {
            return when (packetTypeId.toInt()) {
                4 -> literalContent
                0 -> subPackages.map { it.value }.reduce { a, b -> a.add(b) }
                1 -> subPackages.map { it.value }.reduce { a, b -> a.multiply(b) }
                2 -> subPackages.map { it.value }.minByOrNull { it.longValueExact() }!!
                3 -> subPackages.map { it.value }.maxByOrNull { it.longValueExact() }!!
                5 -> subPackages.map { it.value }.reduce { a, b -> if (a > b) BigInteger.ONE else BigInteger.ZERO }
                6 -> subPackages.map { it.value }.reduce { a, b -> if (a < b) BigInteger.ONE else BigInteger.ZERO }
                7 -> subPackages.map { it.value }
                    .reduce { a, b -> if (a.equals(b)) BigInteger.ONE else BigInteger.ZERO }
                else -> BigInteger.ZERO
            }
        }


        fun parsePacketLength(): BigInteger {
            if (packetTypeId == LITERAL_VALUE_ID) {
                var counter = 6L
                val encodedNums = binaryCode.substring(6)
                if (encodedNums.length < 6) {
                    return (counter + 5).toBigInteger()
                } else {
                    return (counter + encodedNums.chunked(5)
                        .takeWhileInclusive { it[0].equals('1') }.map { 5 }.sum()).toBigInteger()
                }


            } else {
                val lengthTypeId = binaryCode[6].digitToInt()
                if (lengthTypeId == 0) {
                    return binaryCode.substring(7, 22).toBigInteger(2).add(BigInteger.valueOf(22))
                } else {
                    var counter = 18L
                    var remainingCode = binaryCode.substring(18)
                    val numSubpackages = binaryCode.substring(7, 18).toBigInteger(2)
                    for (i in 0..numSubpackages.toInt() - 1) {
                        val subpacket = fromBinary(remainingCode).packetLength
                        remainingCode = remainingCode.substring(subpacket.toInt())
                        counter += subpacket.toLong()
                    }
                    return counter.toBigInteger()
                }
            }
        }

        fun parseLiteralContent(): BigInteger {
            if (packetTypeId == LITERAL_VALUE_ID) {
                val numbers = binaryCode.substring(6, this.packetLength.toInt())
                if (numbers.length < 6) {
                    return numbers.toBigInteger(2)
                }
                return numbers.trimStart('0').chunked(5)
                    .takeWhileInclusive { !it[0].equals('0') }
                    .map { it.substring(1) }
                    .joinToString("")
                    .toBigInteger(2)
            }
            return BigInteger.ZERO
        }

        fun parseSubPackages(): List<Package> {
            return when (packetTypeId) {
                LITERAL_VALUE_ID -> listOf()
                else -> parseOperator()
            }
        }

        fun parseOperator(): List<Package> {
            val lengthTypeId = binaryCode[6].digitToInt()
            if (lengthTypeId == 0) {
                val bitLengthOfPackets = binaryCode.substring(7, 22).toBigInteger(2)
                var subPacketCode = binaryCode.substring(22, 22 + bitLengthOfPackets.toInt())
                val packets = mutableListOf<Package>()
                while (subPacketCode.length > 6) {
                    val pack = fromBinary(subPacketCode)
                    packets.add(pack)
                    subPacketCode = subPacketCode.substring(pack.packetLength.toInt())
                }
                return packets
            } else {
                val numberOfPackets = binaryCode.substring(7, 18).toBigInteger(2)
                var subPacketCode = binaryCode.substring(18)
                val packets = mutableListOf<Package>()
                for (i in 0..numberOfPackets.toLong() - 1) {
                    val pack = fromBinary(subPacketCode)
                    packets.add(pack)
                    subPacketCode = subPacketCode.substring(pack.packetLength.toInt())
                }
                return packets
            }
        }

        fun sumVersionDeep(): BigInteger {
            if (subPackages.isEmpty()) {
                return this.packetVersion
            }
            return subPackages.map { it.sumVersionDeep() }.reduce { a, b -> a.add(b) }.add(this.packetVersion)

        }

        companion object {
            val LITERAL_VALUE_ID = BigInteger.valueOf(4)

            fun fromBinary(code: String): Package {
                return Package(code.toBigInteger(2).toString(16), leadingZeros = code.indexOf('1'))
            }
        }
    }

    override fun part1() {
        val input = Input.readSingleLineAsString(day)
        val result = Package(input, leadingZeros = input.length * 4 - input.toBigInteger(16).toString(2).length)
            .sumVersionDeep()
        println("Day $day-1: $result")
    }


    override fun part2() {
        val input = Input.readSingleLineAsString(day)
        val result = Package(input, leadingZeros = input.length * 4 - input.toBigInteger(16).toString(2).length).value
        println("Day $day-2: $result")
    }
}