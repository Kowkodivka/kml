package ru.kowkodivka.kml

fun main() {
    val evaluator = Evaluator()

    while (true) {
        print("> ")
        val input = readlnOrNull()

        if (input.isNullOrEmpty()) {
            continue
        }

        val lexer = Lexer(input)
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
