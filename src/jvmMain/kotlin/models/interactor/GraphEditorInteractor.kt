package models.interactor

import data.`object`.Graph
import data.`object`.Vertex
import models.repository.GraphRepository
import org.jetbrains.skia.Point

interface GraphEditorInteractor {
    fun getGraph(): Graph
    fun addVertex(vertexName: String, center: Point): Vertex
    fun removeVertex(vertexId: Long)
    fun linkVertexes(vertexId1: Long, vertexId2: Long)
    fun removeLink(vertexId1: Long, vertexId2: Long)
    fun setOrder(vertexId: Long, order: Int)
}

class GraphEditorInteractorImpl(private val graphRepository: GraphRepository): GraphEditorInteractor {
    override fun getGraph(): Graph = graphRepository.getGraph()

    override fun addVertex(vertexName: String, center: Point) = graphRepository.addVertex(vertexName, center)

    override fun removeVertex(vertexId: Long) = graphRepository.removeVertex(vertexId)

    override fun linkVertexes(vertexId1: Long, vertexId2: Long) = graphRepository.linkVertexes(vertexId1, vertexId2)

    override fun removeLink(vertexId1: Long, vertexId2: Long) = graphRepository.removeLink(vertexId1, vertexId2)

    override fun setOrder(vertexId: Long, order: Int) = graphRepository.setOrder(vertexId, order)
}