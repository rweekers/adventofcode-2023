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

    fun traverse(points: List<Pipe>, startingPoint: Pipe): List<Point2D> {
        val startNeighbours = startingPoint.neighbors
            .map { points.first { p -> p.point == it } }
            .filter { it.possibleNeighbors.contains(startingPoint.point) }

        val visited = mutableListOf<Point2D>()
        var currentPoint = startNeighbours[0]

        while (currentPoint != startingPoint) {
            val nextStep = currentPoint.possibleNeighbors
                .first { !visited.contains(it) }
            visited.add(currentPoint.point)
            currentPoint = points.first { it.point == nextStep }
        }
        visited.add(startingPoint.point)

        return visited
    }

    fun part1(input: List<String>): Int {
        val points = parseInput(input)

        val startingPoint = points.first { it.symbol == "S" }

        return traverse(points, startingPoint).size / 2
    }

    fun count(point2DS: List<Pipe>, allPoints: List<Pipe>): Int {
        val t = point2DS/*.filter { it.symbol != "-" }*//*.filter { it.symbol in listOf("|", "S", "F", "J", "L", "7") }*/.windowed(2, 2)

//        val t2 = t.map { it.first().point.x until it. }
        val t2 = t.filter { it.size > 1 }.map { (it[0].point.x+1 until it[1].point.x).map { a ->
            allPoints.filter { p -> p.point.x == a && p.point.y == t.first().map { b -> b.point.y }.first() && p.symbol == "." }
        } }

        val t3 = t2.flatten().sumOf { it.size }



        return 0
    }

    fun part2(input: List<String>): Int {
        val points = parseInput(input)

        val startingPoint = points.first { it.symbol == "S" }

        val pipelinePoints = traverse(points, startingPoint)

        val rows = pipelinePoints.groupBy { it.y }.toSortedMap().toList().sortedBy { it.first }
            .map { it.second.sortedBy { r -> r.x } }

        val pipes = points.filter { it.point in rows.flatten() }

        val pipeRows = pipes.groupBy { it.point.y }.toList().sortedBy { it.first }

        val test = pipeRows.map { count(it.second, points) }.sumOf { it }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput) == 4)
    check(part1(testInput2) == 8)

    check(part2(testInput) == 0)// 1
    check(part2(testInput2) == 0) // 1
    val testInput3 = readInput("Day10_test3")
    check(part2(testInput3) == 0) // 4
    val testInput4 = readInput("Day10_test4")
    check(part2(testInput4) == 0) // 8
    val testInput5 = readInput("Day10_test5")
    check(part2(testInput5) == 0) // 10

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
            "L" -> listOf(Point2D(point.x, point.y - 1), Point2D(point.x + 1, point.y))
            "J" -> listOf(Point2D(point.x, point.y - 1), Point2D(point.x - 1, point.y))
            "7" -> listOf(Point2D(point.x - 1, point.y), Point2D(point.x, point.y + 1))
            "F" -> listOf(Point2D(point.x + 1, point.y), Point2D(point.x, point.y + 1))
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
