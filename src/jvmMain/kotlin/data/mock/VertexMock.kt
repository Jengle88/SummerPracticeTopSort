package data.mock

import org.jetbrains.skia.Point


class VertexMock(
    private val name: String,
    var order: Int,
    val center: Point,
    private val edges: ArrayList<String>
) {
    fun getName(): String = name

    fun removeEdge(edge: String) = edges.remove(edge)
    fun addEdge(edge: String) {
        edges.add(edge)
    }

    fun getEdges() = edges
}

