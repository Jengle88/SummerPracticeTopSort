package models.interactor

import data.mock.VertexMock
import models.repository.GraphRepository
import org.jetbrains.skia.Point

// TODO: 06.07.2022 Нужно сохранять позиции вершин графа в файл
interface GraphEditorInteractor {
    fun getGraph(): ArrayList<VertexMock>
    fun addVertex(vertexName: String, center: Point) // TODO: 06.07.2022 Заменить на VertexDTO
    fun removeVertex(vertexName: String)
    fun linkVertexes(vertexName1: String, vertexName2: String)
    fun removeLink(vertexName1: String, vertexName2: String)
    fun setOrder(vertexName: String, order: Int)
}

class GraphEditorInteractorImpl(private val graphRepository: GraphRepository): GraphEditorInteractor {
    override fun getGraph(): ArrayList<VertexMock> = graphRepository.getGraph()

    override fun addVertex(vertexName: String, center: Point) = graphRepository.addVertex(vertexName, center)

    override fun removeVertex(vertexName: String) = graphRepository.removeVertex(vertexName)

    override fun linkVertexes(vertexName1: String, vertexName2: String) = graphRepository.linkVertexes(vertexName1, vertexName2)
    override fun removeLink(vertexName1: String, vertexName2: String) = graphRepository.removeLink(vertexName1, vertexName2)

    override fun setOrder(vertexName: String, order: Int) = graphRepository.setOrder(vertexName, order)
}