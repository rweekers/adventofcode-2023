fun main() {

    fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { it.split(" ").map { n -> n.toInt() } }
    }

    fun calculate(numbers: List<Int>): Int {
        if (numbers.all { it == 0 }) {
            return 0
        }
        val differences = numbers.windowed(2, 1).map { it[1] - it[0] }
        return numbers.last() + calculate(differences)
    }

    fun part1(input: List<String>): Int {
        return parseInput(input)
            .sumOf { calculate(it) }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .map { it.reversed() }
            .sumOf { calculate(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
