package ru.kowkodivka.kml

class Parser(private val lexer: Lexer, private val evaluator: Evaluator) {
    private var currentToken: Node.Token? = null

    fun parse(): Node {
        advance()
        return parseExpression()
    }

    private fun advance() {
        currentToken = lexer.nextToken()
    }

    private fun parseExpression(): Node {
        val left = parseTerm()
        return parseExpressionRest(left)
    }

    private fun parseExpressionRest(left: Node): Node {
        return when (currentToken) {
            Node.Token.Plus -> {
                advance()
                val term = parseTerm()
                val addNode = Node.Add(left, term)
                parseExpressionRest(addNode)
            }

            else -> left
        }
    }

    private fun parseTerm(): Node {
        return when (val token = currentToken) {
            is Node.Token.Number -> {
                advance()
                Node.Token.Number(token.value)
            }

            is Node.Token.Identifier -> {
                advance()
                if (currentToken == Node.Token.Assign) {
                    advance()
                    val expression = parseExpression()
                    evaluator.setVariable(token.name, evaluator.evaluate(expression))
                    expression
                } else {
                    Node.Token.Identifier(token.name)
                }
            }

            else -> throw IllegalArgumentException("Invalid token: $token")
        }
    }
}
