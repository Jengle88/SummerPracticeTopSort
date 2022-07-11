package models.interactor

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import models.repository.GraphEditorRepository
import org.jetbrains.skia.Point

class GraphEditorInteractorImpl(private val graphEditorRepository: GraphEditorRepository):
    GraphEditorInteractor {
    override fun getGraph(): MutableStateFlow<Graph> = graphEditorRepository.getGraph()

    override fun addVertex(vertexName: String, center: Point) = graphEditorRepository.addVertex(vertexName, center)

    override fun removeVertex(vertexId: Long) = graphEditorRepository.removeVertex(vertexId)

    override fun linkVertexes(vertexId1: Long, vertexId2: Long) = graphEditorRepository.linkVertexes(vertexId1, vertexId2)

    override fun removeLink(vertexId1: Long, vertexId2: Long) = graphEditorRepository.removeLink(vertexId1, vertexId2)

    override fun setOrder(vertexId: Long, order: Int) = graphEditorRepository.setOrder(vertexId, order)
}