fun main() {

    fun parseInput(input: List<String>): List<Pipe> {
        val pointStrings = input.map { it.split("").filter { n -> n.isNotEmpty() } }
        val points = mutableListOf<Pipe>()

        val maxX = pointStrings[0].size
        val maxY = pointStrings.size

        pointStrings.forEachIndexed { y, row ->
            row.forEachIndexed { x, pipe ->
                points.add(Pipe(Point2D(x, y), pipe, maxX, maxY))
            }
        }

        return points
    }

    fun part1(input: List<String>): Int {
        val points = parseInput(input)

        val startingPoint = points.first { it.symbol == "S" }

        val startNeighbours = startingPoint.neighbors
            .map { points.first { p -> p.point == it } }
            .filter { it.possibleNeighbors.contains(startingPoint.point) }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput) == 0)
    check(part1(testInput2) == 0)
    check(part2(testInput) == 0)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

data class Pipe(val point: Point2D, val symbol: String, val maxX: Int, val maxY: Int) {
    val possibleNeighbors: List<Point2D> = possibleNeighbors().filter { it.x in 0 ..< maxX && it.y in 0 ..< maxY }
    val neighbors = neighbors().filter { it.x in 0 ..< maxX && it.y in 0 ..< maxY }

    private fun possibleNeighbors(): List<Point2D> {
        return when(symbol) {
            "|" -> listOf(Point2D(point.x, point.y + 1), Point2D(point.x, point.y - 1))
            "-" -> listOf(Point2D(point.x + 1, point.y), Point2D(point.x - 1, point.y))
            "L" -> listOf(Point2D(point.x, point.y + 1), Point2D(point.x + 1, point.y))
            "J" -> listOf(Point2D(point.x, point.y + 1), Point2D(point.x - 1, point.y))
            "7" -> listOf(Point2D(point.x - 1, point.y), Point2D(point.x, point.y - 1))
            "F" -> listOf(Point2D(point.x + 1, point.y), Point2D(point.x, point.y - 1))
            "." -> listOf()
            "S" -> listOf()
            else -> throw IllegalArgumentException("$symbol is not known")
        }
    }

    private fun neighbors(): List<Point2D> {
        return point.neighbors().toList()
    }
}

data class Point2D(val x: Int, val y: Int) {
    fun neighbors(): Set<Point2D> =
        setOf(
            Point2D(x, y - 1),
            Point2D(x - 1, y),
            Point2D(x + 1, y),
            Point2D(x, y + 1)
        )
}
