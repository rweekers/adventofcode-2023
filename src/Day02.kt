fun main() {

    fun parseInput(input: List<String>): List<Game> =
        input
            .asSequence()
            .map { it.split(":") }
            .map { it[0] to it[1] }
            .map { it.first to it.second.split(";") }
            .map { it.first to it.second.map { r -> Reveal.fromString(r) } }
            .map { Game(it.first.substring(it.first.indexOf(" ") + 1).toInt(), it.second) }
            .toList()

    fun part1(input: List<String>): Int {
        val maxBlue = 14
        val maxGreen = 13
        val maxRed = 12

        val games = parseInput(input)

        val filteredGames = games
            .filter { game -> game.reveals.all { r -> r.withinBounds(maxBlue, maxGreen, maxRed) } }

        return filteredGames.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        val games = parseInput(input)

        return games
            .map {
                it.reveals.fold(Triple(0, 0, 0)) { acc, curr ->
                    Triple(
                        if (curr.blue > acc.first) curr.blue else acc.first,
                        if (curr.green > acc.second) curr.green else acc.second,
                        if (curr.red > acc.third) curr.red else acc.third
                    )
                }
            }.sumOf { it.first * it.second * it.third }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Game(val number: Int, val reveals: List<Reveal> = emptyList())
data class Reveal(val blue: Int, val green: Int, val red: Int) {

    fun withinBounds(maxblue: Int, maxGreen: Int, maxRed: Int): Boolean {
        return blue <= maxblue && green <= maxGreen && red <= maxRed
    }

    companion object {
        fun fromString(a: String): Reveal {
            val colours: Map<String, Int> =
                a.split(",").map { b -> b.split(" ").subList(1, 3) }.associate { it[1].trim() to it[0].toInt() }

            val blue = colours["blue"] ?: 0
            val green = colours["green"] ?: 0
            val red = colours["red"] ?: 0

            return Reveal(blue, green, red)
        }
    }
}
