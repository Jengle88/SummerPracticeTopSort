package models.repository

import data.`object`.Graph
import data.`object`.Vertex
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skia.Point

interface GraphEditorRepository {
    fun getGraph(): MutableStateFlow<Graph>
    fun addVertex(vertexName: String, center: Point): Vertex
    fun removeVertex(vertexId: Long)
    fun linkVertexes(vertexId1: Long, vertexId2: Long)
    fun removeLink(vertexId1: Long, vertexId2: Long)
    fun setOrder(vertexId: Long, order: Int)
}