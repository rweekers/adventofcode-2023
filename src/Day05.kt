fun main() {

    fun parseInput(input: List<String>, parseSeeds: (String) -> List<Long>): Pair<List<Long>, List<SeedMapper>> {
        val seeds = parseSeeds(input[0])
        val mappings = input.subList(2, input.size).filter { it.isNotEmpty() }
            .fold(mutableListOf<SeedMapper>()) { acc, curr ->
                if (!curr.first().isDigit()) {
                    acc.add(SeedMapper())
                } else {
                    val n = curr.split(" ").map { it.trim().toLong() }
                    val seedMapper = acc.last()

                    seedMapper.addSeedRange(SeedRange(n[1], n[2], n[0] - n[1]))
                }

                acc
            }
        return seeds to mappings
    }

    fun parseSeedsSilver(s: String): List<Long> {
        return s.substring(6).split(" ").filter { it.isNotEmpty() }.map {
            it.trim().toLong()
        }
    }

    fun part1(input: List<String>): Long {
        val (seeds, mappings) = parseInput(input) { parseSeedsSilver(it) }

        val locations = seeds.map { seed ->
            mappings.fold(seed) { acc, curr ->
                curr.mapSource(acc)
            }
        }

        return locations.min()
    }

    fun parseSeedsGold(s: String): List<Long> {
        val seeds = s.substring(6).split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
        return listOf(seeds[0], seeds[1], seeds[2], seeds[3])
    }

    fun part2(input: List<String>): Long {
        val (seeds, mappings) = parseInput(input) { parseSeedsGold(it) }

        var count = 0L
        while (true) {
            val v = mappings.reversed().fold(count) { acc, curr ->
                curr.mapDestination(acc)
            }
            if ((v >= seeds[0] && v < seeds[0] + seeds[1]) || (v >= seeds[2] && v < seeds[2] + seeds[3])) {
                return count
            }
            count++
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class SeedMapper(val ranges: MutableList<SeedRange> = mutableListOf()) {
    fun addSeedRange(seedRange: SeedRange) {
        this.ranges.add(seedRange)
    }

    fun mapSource(source: Long): Long {
        val destination = ranges.map { it.mapToDestination(source) }.firstOrNull { it != source }

        return destination ?: source
    }

    fun mapDestination(source: Long): Long {
        val destination = ranges.map { it.mapToSource(source) }.firstOrNull { it != source }

        return destination ?: source
    }
}

data class SeedRange(val source: Long, val range: Long, val offset: Long) {
    fun mapToDestination(s: Long): Long {
        return if (s >= source && s <= source + range) {
            s + offset
        } else {
            s
        }
    }

    fun mapToSource(s: Long): Long {
        return if (s >= source + offset && s <= source + offset + range) {
            s - offset
        } else {
            s
        }
    }
}