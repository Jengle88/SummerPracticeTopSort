package data.repositoryImpl

import data.mock.VertexMock
import data.mock.graph
import models.repository.GraphRepository
import org.jetbrains.skia.Point

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

    override fun removeLink(vertexName1: String, vertexName2: String) {
        if (graph.find { it.getName() == vertexName1 }
                ?.removeEdge(vertexName2) != true)
            graph.find { it.getName() == vertexName2 }?.removeEdge(vertexName1)
    }

    override fun setOrder(vertexName: String, order: Int) {
        graph.find { it.getName() == vertexName }?.order = order
    }
}