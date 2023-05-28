package ru.kowkodivka.kml

sealed class Node {
    sealed class Token : Node() {
        object Plus : Token()
        data class Number(val value: Int) : Token()
    }

    data class Add(val left: Node, val right: Node) : Node()
}

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
            in '0'..'9' -> {
                var num = c.toString()

                while (position < input.length && input[position].isDigit()) {
                    num += input[position]
                    position++
                }

                Node.Token.Number(num.toInt())
            }

            else -> throw IllegalArgumentException("Invalid character: $c")
        }
    }

    private fun skipWhitespaces() {
        while (position < input.length && input[position].isWhitespace()) {
            position++
        }
    }
}

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


class Evaluator {
    fun evaluate(node: Node): Int {
        return when (node) {
            is Node.Token.Number -> node.value
            is Node.Add -> evaluate(node.left) + evaluate(node.right)
            else -> throw IllegalArgumentException("Invalid node: $node")
        }
    }
}

fun main() {
    while (true) {
        print("> ")
        val input = readln()

        val lexer = Lexer(input)
        val parser = Parser(lexer)
        val evaluator = Evaluator()

        val ast = parser.parse()
        val result = evaluator.evaluate(ast)

        println("Result: $result")
    }
}
