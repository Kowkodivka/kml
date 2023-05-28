package ru.kowkodivka.kml

import java.io.File

fun main() {
    val evaluator = Evaluator()
    val file = File("code.kml")

    if (!file.exists()) {
        println("File 'code.kml' not found.")
        return
    }

    file.forEachLine { line ->
        val lexer = Lexer(line)
        val parser = Parser(lexer, evaluator)

        try {
            val ast = parser.parse()
            val result = evaluator.evaluate(ast)

            println("Result: $result")
        } catch (e: IllegalArgumentException) {
            println("Error: ${e.message}")
        }
    }
}
