package ru.kowkodivka.kml

class Lexer(private val input: String) {
    private var position = 0

    fun nextToken(): Node.Token? {
        skipWhitespaces()

        if (position >= input.length) {
            return null
        }

        val c = input[position]
        position++

        return when (c) {
            '+' -> Node.Token.Plus
            '-' -> Node.Token.Minus
            '=' -> Node.Token.Assign

            in '0'..'9' -> {
                var num = c.toString()

                while (position < input.length && input[position].isDigit()) {
                    num += input[position]
                    position++
                }

                Node.Token.Number(num.toInt())
            }

            else -> {
                if (c.isLetter()) {
                    var identifier = c.toString()

                    while (position < input.length && (input[position].isLetterOrDigit() || input[position] == '_')) {
                        identifier += input[position]
                        position++
                    }

                    Node.Token.Identifier(identifier)
                } else {
                    throw IllegalArgumentException("Invalid character: $c")
                }
            }
        }
    }

    private fun skipWhitespaces() {
        while (position < input.length && input[position].isWhitespace()) {
            position++
        }
    }
}