package data.repositoryImpl

import data.mock.VertexMock
import data.mock.graph
import models.mapper.toVertexVO
import models.repository.GraphRepository
import org.jetbrains.skia.Point
import ui.GraphEditor.GraphCanvas.VertexVO

class GraphRepositoryImpl: GraphRepository {
    override fun getGraph(): ArrayList<VertexMock> = graph

    override fun addVertex(vertexName: String, center: Point) {
        graph.add(VertexMock(
                name = vertexName,
                order = -1,
                center = center,
                edges = arrayListOf()
            )
        )
    }

    override fun removeVertex(vertexName: String) {
        val indexOfElem = graph.indexOfFirst { it.getName() == vertexName }
        if (indexOfElem != -1)
            graph.removeAt(indexOfElem)
    }

    override fun linkVertexes(vertexName1: String, vertexName2: String) {
        graph.find { it.getName() == vertexName1 }?.addEdge(vertexName2)
    }

    override fun setOrder(vertexName: String, order: Int) {
        graph.find { it.getName() == vertexName }?.order = order
    }
}