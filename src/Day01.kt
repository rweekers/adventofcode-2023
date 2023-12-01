fun main() {
    val numberMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
        "zero" to "0"
    )

    fun determineValues(input: List<String>): Int = input.sumOf { "${it.first()}${it.last()}".toInt() }

    fun part1(input: List<String>): Int {
        return determineValues(input
            .map {
                Regex("[^0-9]").replace(it, "")
            }
        )
    }

    fun part2(input: List<String>): Int {
        return determineValues(input
            .map {
                var s = ""
                it.windowed(it.length, partialWindows = true)
                    .forEach { a ->
                        val b = a.substring(0, 1)
                        if (b.isInteger()) {
                            s += b
                        } else {
                            numberMap.entries.forEach { n ->
                                if (a.startsWith(n.key)) {
                                    s += n.value
                                }
                            }
                        }
                    }
                s
            })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testInput2 = readInput("Day01_test2")
    check(part1(testInput) == 142)
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
