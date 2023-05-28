package ru.kowkodivka.kml

sealed class Node {
    sealed class Token : Node() {
        object Plus : Token()
        data class Number(val value: Int) : Token()
    }

    data class Add(val left: Node, val right: Node) : Node()
}