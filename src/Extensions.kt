val integerChars = '0'..'9'

fun String.isInteger(): Boolean {
    return this.all { it in integerChars }
}