fun main() {

    fun part1(input: List<String>): Int {
        val cards = input.map {
            val handToBid = it.split(" ")
            Hand(handToBid[0].toList().map { c -> CamelCard(c.toString()) }, handToBid[1].toInt())
        }
            .sortedWith { o1, o2 -> o1.compareTo(o2) }

        return cards
            .reversed()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val cards = input.map {
            val handToBid = it.split(" ")
            Hand(handToBid[0].toList().map { c -> CamelCard(c.toString()) }, handToBid[1].toInt())
        }
            .sortedWith { o1, o2 -> o1.compareToGold(o2) }

        return cards
            .reversed()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

data class Hand(val cards: List<CamelCard>, val bid: Int) {

    private val type: HandType = determineType()
    private val goldType: HandType = determineTypeGold()

    private fun determineType(): HandType {
        val cardGroups = cards.groupBy { it.symbol }.values.sortedByDescending { it.size }

        return if (cardGroups.size == 1) {
            HandType.FIVE_OF_A_KIND
        } else if (cardGroups[0].size == 4) {
            HandType.FOUR_OF_A_KIND
        } else if (cardGroups[0].size == 3 && cardGroups[1].size == 2) {
            HandType.FULL_HOUSE
        } else if (cardGroups[0].size == 3 && cardGroups[1].size == 1) {
            HandType.THREE_OF_A_KIND
        } else if (cardGroups[0].size == 2 && cardGroups[1].size == 2) {
            HandType.TWO_PAIR
        } else if (cardGroups[0].size == 2 && cardGroups[1].size == 1) {
            HandType.ONE_PAIR
        } else {
            HandType.HIGH_CARD
        }
    }

    private fun determineTypeGold(): HandType {
        val cardGroups = cards.groupBy { it.symbol }.values.sortedByDescending { it.size }

        val jokers = cards.filter { it.symbol == "J" }

        val updated = cardGroups.map { it.filter { c -> c.symbol != "J" } }.toMutableList()

        updated[0] = updated[0] + jokers

        return if (updated.size == 1) {
            HandType.FIVE_OF_A_KIND
        } else if (updated[0].size == 4) {
            HandType.FOUR_OF_A_KIND
        } else if (updated[0].size == 3 && updated[1].size == 2) {
            HandType.FULL_HOUSE
        } else if (updated[0].size == 3 && updated[1].size == 1) {
            HandType.THREE_OF_A_KIND
        } else if (updated[0].size == 2 && updated[1].size == 2) {
            HandType.TWO_PAIR
        } else if (updated[0].size == 2 && updated[1].size == 1) {
            HandType.ONE_PAIR
        } else {
            HandType.HIGH_CARD
        }
    }

    fun compareToGold(other: Hand): Int {
        return if (this.goldType > other.goldType) {
            1
        } else if (this.goldType < other.goldType) {
            -1
        } else {
            // compare cards
            val firstMismatchIndex = cards.zip(other.cards).indexOfFirst { (a, b) -> a != b }

            return other.cards[firstMismatchIndex].compareToGold(cards[firstMismatchIndex])
        }
    }

    fun compareTo(other: Hand): Int {
        return if (this.type > other.type) {
            1
        } else if (this.type < other.type) {
            -1
        } else {
            // compare cards
            val firstMismatchIndex = cards.zip(other.cards).indexOfFirst { (a, b) -> a != b }

            return other.cards[firstMismatchIndex].compareTo(cards[firstMismatchIndex])
        }
    }
}

enum class HandType(val value: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

data class CamelCard(val symbol: String) {

    fun compareTo(other: CamelCard): Int {
        return if (this.toValue() > other.toValue()) {
            1
        } else if (this.toValue() < other.toValue()) {
            -1
        } else {
            0
        }
    }

    fun compareToGold(other: CamelCard): Int {
        return if (this.toValueGold() > other.toValueGold()) {
            1
        } else if (this.toValueGold() < other.toValueGold()) {
            -1
        } else {
            0
        }
    }

    private fun toValueGold(): Int {
        return when (symbol) {
            "1" -> 1
            "2" -> 2
            "3" -> 3
            "4" -> 4
            "5" -> 5
            "6" -> 6
            "7" -> 7
            "8" -> 8
            "9" -> 9
            "T" -> 10
            "J" -> 1
            "Q" -> 12
            "K" -> 13
            "A" -> 14
            else -> throw IllegalArgumentException("Symbol $symbol not known")
        }
    }

    private fun toValue(): Int {
        return when (symbol) {
            "1" -> 1
            "2" -> 2
            "3" -> 3
            "4" -> 4
            "5" -> 5
            "6" -> 6
            "7" -> 7
            "8" -> 8
            "9" -> 9
            "T" -> 10
            "J" -> 11
            "Q" -> 12
            "K" -> 13
            "A" -> 14
            else -> throw IllegalArgumentException("Symbol $symbol not known")
        }
    }

}
