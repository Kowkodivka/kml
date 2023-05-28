package ru.kowkodivka.kml

class Evaluator {
    private val variables = mutableMapOf<String, Int>()

    fun evaluate(node: Node): Int {
        return when (node) {
            is Node.Token.Number -> node.value
            is Node.Token.Identifier -> getVariableValue(node.name)
            is Node.Add -> evaluate(node.left) + evaluate(node.right)
            is Node.Subtract -> evaluate(node.left) - evaluate(node.right)
            else -> throw IllegalArgumentException("Invalid node: $node")
        }
    }

    fun setVariable(name: String, value: Int) {
        variables[name] = value
    }

    private fun getVariableValue(name: String): Int {
        return variables[name] ?: throw IllegalArgumentException("Undefined variable: $name")
    }
}