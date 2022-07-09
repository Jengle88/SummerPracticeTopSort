package data.repositoryImpl

import data.`object`.Graph
import data.`object`.Vertex
import kotlinx.coroutines.flow.MutableStateFlow
import models.repository.GraphEditorRepository
import org.jetbrains.skia.Point

class GraphEditorRepositoryImpl(private val graphFlow: MutableStateFlow<Graph>): GraphEditorRepository {
    private fun getFreeId() = (graphFlow.value.getVertexes().maxOfOrNull { it.getId() } ?: 0) + 1
    override fun getGraph(): MutableStateFlow<Graph> = graphFlow

    override fun addVertex(vertexName: String, center: Point): Vertex {
        val vertex = Vertex(
            id = getFreeId(),
            name = vertexName,
            order = -1,
            center = center,
            edges = arrayListOf()
        )
        graphFlow.value.addVertex(vertex)
        return vertex
    }

    override fun removeVertex(vertexId: Long) {
        graphFlow.value.removeVertexById(vertexId)
    }

    override fun linkVertexes(vertexId1: Long, vertexId2: Long) {
        graphFlow.value.linkVertexes(vertexId1, vertexId2)
    }

    override fun removeLink(vertexId1: Long, vertexId2: Long) {
        graphFlow.value.removeLink(vertexId1, vertexId2)
    }

    override fun setOrder(vertexId: Long, order: Int) {
        graphFlow.value.setOrderToVertex(vertexId, order)
    }
}