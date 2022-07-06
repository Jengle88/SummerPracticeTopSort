package models.repository

import data.mock.VertexMock
import org.jetbrains.skia.Point
import ui.GraphEditor.GraphCanvas.VertexVO

interface GraphRepository {
    fun getGraph(): ArrayList<VertexMock> // TODO: 06.07.2022 Заменить VertexVO на VertexDTO
    fun addVertex(vertexName: String, center: Point) // TODO: 06.07.2022 Заменить String на VertexDTO
    fun removeVertex(vertexName: String)
    fun linkVertexes(vertexName1: String, vertexName2: String)
    fun setOrder(vertexName: String, order: Int)
}