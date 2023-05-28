import ru.kowkodivka.kml.Evaluator
import ru.kowkodivka.kml.Lexer
import ru.kowkodivka.kml.Parser

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