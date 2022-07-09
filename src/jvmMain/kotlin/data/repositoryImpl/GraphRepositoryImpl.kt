package data.repositoryImpl

import data.`object`.Graph
import data.`object`.Vertex
import models.repository.GraphRepository
import org.jetbrains.skia.Point
import kotlin.properties.Delegates

class GraphRepositoryImpl(private val graph: Graph): GraphRepository {
    companion object {
        var maxAvailableId by Delegates.notNull<Long>()
    }
    init {
        maxAvailableId = (graph.getVertexes().maxOfOrNull { it.getId() } ?: 0) + 1

    }
    override fun getGraph(): Graph = graph

    override fun addVertex(vertexName: String, center: Point): Vertex {
        val vertex = Vertex(
            id = maxAvailableId++,
            name = vertexName,
            order = -1,
            center = center,
            edges = arrayListOf()
        )
        graph.addVertex(vertex)
        return vertex
    }

    override fun removeVertex(vertexId: Long) {
        graph.removeVertexById(vertexId)
    }

    override fun linkVertexes(vertexId1: Long, vertexId2: Long) {
        graph.linkVertexes(vertexId1, vertexId2)
    }

    override fun removeLink(vertexId1: Long, vertexId2: Long) {
        graph.removeLink(vertexId1, vertexId2)
    }

    override fun setOrder(vertexId: Long, order: Int) {
        graph.setOrderToVertex(vertexId, order)
    }
}