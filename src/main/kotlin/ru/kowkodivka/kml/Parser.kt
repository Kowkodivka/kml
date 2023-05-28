package ru.kowkodivka.kml

import ru.kowkodivka.kml.tokens.Add
import ru.kowkodivka.kml.tokens.Num
import ru.kowkodivka.kml.tokens.Token

class Parser(private val lexer: Lexer) {
    fun parse(): Node {
        val token = lexer.nextToken() ?: throw IllegalArgumentException("Empty input")

        return when (token) {
            is Node.Token.Number -> Node.NumberNode(token.value)
            Node.Token.Plus -> {
                val left = parse()
                val right = parse()
                Node.Add(left, right)
            }
        }
    }
}

