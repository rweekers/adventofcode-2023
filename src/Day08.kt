fun main() {

    fun mapInput(b: List<String>): Pair<String, Pair<String, String>> {
        val p = Pair(b[1], b[2])
        val p2 = Pair(b[0], p)

        return p2
    }

    fun parseInput(input: List<String>): Pair<Iterator<Char>, Map<String, Pair<String, String>>> {
        val directions = generateSequence { input[0].toList() }.flatten().iterator()
        val routes = input.subList(2, input.size)
            .map { it.replace("=", "").replace("(", "").replace(")", "").replace(",", "") }
            .map { it.split(" ").filter { d -> d.isNotEmpty() } }
            .associate { mapInput(it) }
        return directions to routes
    }

    fun calculateSteps(directions: Iterator<Char>, routes: Map<String, Pair<String, String>>, stopCondition: (String) -> Boolean, start: String = "AAA"): Int {
        var e = start
        var count = 0

        while (true) {
            count++
            val direction = directions.next()

            e = if (direction == 'L') {
                routes[e]!!.first
            } else routes[e]!!.second

            if (stopCondition(e)) {
                return count
            }
        }
    }

    fun part1(input: List<String>): Int {
        val (directions, routes) = parseInput(input)

        val stopCondition: (String) -> Boolean = { str -> str == "ZZZ" }

        return calculateSteps(directions, routes, stopCondition)
    }

    fun part2(input: List<String>): Long {
        val (directions, routes) = parseInput(input)

        val startNodes = routes.filter { it.key.last() == 'A' }
        val stopCondition: (String) -> Boolean = { str -> str.last() == 'Z' }

        val steps = startNodes.map { calculateSteps(directions, routes, stopCondition, it.key) }.map { it.toLong() }


        return lcm(steps)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput) == 2)
    check(part1(testInput2) == 6)

    val testInput3 = readInput("Day08_test3")
    check(part2(testInput3) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
