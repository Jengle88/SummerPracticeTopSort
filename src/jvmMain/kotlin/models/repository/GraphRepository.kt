package models.repository

import data.`object`.Graph
import data.`object`.Vertex
import org.jetbrains.skia.Point

interface GraphRepository {
    fun getGraph(): Graph
    fun addVertex(vertexName: String, center: Point): Vertex
    fun removeVertex(vertexId: Long)
    fun linkVertexes(vertexId1: Long, vertexId2: Long)
    fun removeLink(vertexId1: Long, vertexId2: Long)
    fun setOrder(vertexId: Long, order: Int)
}