package ru.kowkodivka.kml

class Evaluator {
    fun evaluate(node: Node): Int {
        return when (node) {
            is Node.Token.Number -> node.value
            is Node.Add -> evaluate(node.left) + evaluate(node.right)
            else -> throw IllegalArgumentException("Invalid node: $node")
        }
    }
}