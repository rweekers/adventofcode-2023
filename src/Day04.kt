import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): List<Card> =
        input
            .map { it.split(":") }
            .map { it[0] to it[1].split("|") }
            .map { p ->
                Card(
                    p.first.substring(5).trim().toInt(),
                    p.second[0].split(" ").map { w -> w.trim() }.filter { it.isNotEmpty() }.map { it.toInt() },
                    p.second[1].split(" ").map { n -> n.trim() }.filter { it.isNotEmpty() }.map { it.toInt() })
            }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .asSequence()
            .map { it.n to it.numbers.filter { n -> it.winningNumbers.contains(n) } }
            .map { it.second.count() }
            .filter { it > 0 }
            .map { 2.toDouble().pow(it.toDouble() - 1) }
            .sum()
            .toInt()
    }

    fun part2(input: List<String>): Int {
        val cards = parseInput(input)
            .associate { it.n to Pair(1, it.numbers.count { n -> it.winningNumbers.contains(n) }) }
            .toMutableMap()

        cards.forEach {
            (1..it.value.first).forEach { _ ->
                (it.key + 1..it.key + it.value.second).forEach { idx ->
                    cards[idx] = Pair(
                        cards[idx]?.first!! + 1,
                        cards[idx]?.second!!
                    )
                }
            }
        }

        return cards.map { it.value.first }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Card(val n: Int, val winningNumbers: List<Int>, val numbers: List<Int>)
