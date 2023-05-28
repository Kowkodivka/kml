package ru.kowkodivka.kml

class Parser(private val lexer: Lexer) {
    fun parse(): Node {
        val token = lexer.nextToken() ?: throw IllegalArgumentException("Empty input")
        return parseExpression(token)
    }

    private fun parseExpression(token: Node.Token): Node {
        val left = parseTerm(token)
        return parseExpressionRest(left)
    }

    private fun parseExpressionRest(left: Node): Node {
        return when (lexer.nextToken()) {
            Node.Token.Plus -> {
                val right = lexer.nextToken() ?: throw IllegalArgumentException("Incomplete expression")
                val term = parseTerm(right)
                val addNode = Node.Add(left, term)
                parseExpressionRest(addNode)
            }

            else -> left
        }
    }

    private fun parseTerm(token: Node.Token): Node {
        return when (token) {
            is Node.Token.Number -> Node.Token.Number(token.value)
            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }
}