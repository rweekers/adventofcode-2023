fun main() {

    fun calculateWays(times: List<Long>, distances: List<Long>): Long {
        val boats = mutableListOf<Boat>()

        times.forEachIndexed { index, i ->
            boats.add(Boat(i, distances[index]))
        }

        return boats
            .map { it.distances().size }
            .fold(1) { acc, curr ->
                curr * acc
            }
    }

    fun part1(input: List<String>): Long {
        val times = input[0].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }
        val distances = input[1].substringAfter(":").split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }

        return calculateWays(times, distances)
    }

    fun part2(input: List<String>): Long {
        val time = input[0].substringAfter(":").replace("\\s".toRegex(), "").toLong()
        val distance = input[1].substringAfter(":").replace("\\s".toRegex(), "").toLong()

        return calculateWays(listOf( time), listOf(distance))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

data class Boat(val time: Long, val distance: Long) {
    fun distances(): List<Long> =
        (1 until time).map { calculateDistance(it) }.filter { it > distance }

    private fun calculateDistance(holdTime: Long): Long =
        holdTime * (time - holdTime)

}
