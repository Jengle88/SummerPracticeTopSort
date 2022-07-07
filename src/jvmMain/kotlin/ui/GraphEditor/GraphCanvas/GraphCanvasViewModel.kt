package ui.GraphEditor.GraphCanvas

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Canvas
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.GraphEditorInteractor
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import utils.CanvasDrawLib
import utils.EditorState
import utils.getDistTo

// TODO: 06.07.2022 Добавить interactor
class GraphCanvasViewModel(
    private val editorStateFlow: MutableStateFlow<EditorState>,
    private val graphEditorInteractor: GraphEditorInteractor,
    private val graphVertex: SnapshotStateList<VertexVO>
) {
    private val font = Font().apply {
        size = 15f
    }
    private var firstVertexForEdge: VertexVO? = null
    fun drawGraph(canvas: Canvas, value: List<VertexVO>) {
        val mapVertex: MutableMap<String, Point> = mutableMapOf()
        for (vertex in value) {
            CanvasDrawLib.drawVertex(canvas, vertex, font)
            mapVertex[vertex.name] = vertex.center
        }
        for (vertex in value) {
            val listOfEdges = graphEditorInteractor.getGraph().find { it.getName() == vertex.name }
            for (otherVertex in listOfEdges?.getEdges() ?: ArrayList()) {
                CanvasDrawLib.drawEdge(canvas, mapVertex[vertex.name], mapVertex[otherVertex])
            }
        }
    }

    fun selectPoint(
        point: Point,
        height: Int,
        width: Int
    ) {
            when(editorStateFlow.value) {
                EditorState.SET_VERTEX -> {
                    firstVertexForEdge = null
                    addVertexToCanvas(point, height, width)
                }
                EditorState.REMOVE_VERTEX -> {
                    firstVertexForEdge = null
                    removeVertexFromCanvas(point)
                }
                EditorState.SET_EDGE_FIRST -> {
                    setFirstEdgePointAdd(point)
                }
                EditorState.SET_EDGE_SECOND -> {
                    setSecondEdgePointForAdd(point)
                }
                EditorState.REMOVE_EDGE_FIRST -> {
                    setFirstEdgePointForRemove(point)
                }
                EditorState.REMOVE_EDGE_SECOND -> {
                    setSecondEdgePointForRemove(point)
                }
                else -> {}
        }
    }


    private fun setFirstEdgePointAdd(point: Point) {
        firstVertexForEdge =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null)
            editorStateFlow.value = EditorState.SET_EDGE_SECOND
    }

    private fun setSecondEdgePointForAdd(point: Point) {
        val secondVertex =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null && secondVertex != null) {
            graphEditorInteractor.linkVertexes(
                firstVertexForEdge!!.name,
                secondVertex.name
            )
            editorStateFlow.value = EditorState.SET_EDGE_FIRST
            // уведомляем об изменении графа
            graphVertex[graphVertex.lastIndex] = graphVertex.last()
            firstVertexForEdge = null
        }
    }

    private fun setFirstEdgePointForRemove(point: Point) {
        firstVertexForEdge =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null)
            editorStateFlow.value = EditorState.REMOVE_EDGE_SECOND
    }

    private fun setSecondEdgePointForRemove(point: Point) {
        val secondVertex =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null && secondVertex != null) {
            graphEditorInteractor.removeLink(
                firstVertexForEdge!!.name,
                secondVertex.name
            )
            editorStateFlow.value = EditorState.REMOVE_EDGE_FIRST
            // уведомляем об изменении графа
            graphVertex[graphVertex.lastIndex] = graphVertex.last()
            firstVertexForEdge = null
        }
    }

    private fun addVertexToCanvas(
        point: Point,
        height: Int,
        width: Int
    ) {
        if (checkVertexPosition(point, height, width)) {

            // TODO: 06.07.2022 Получение названия вершины...
            val nameMock = "vertex${graphVertex.size + 1}"
            val vertex = VertexVO(name = nameMock, center = point)
            graphVertex.add(vertex)
            graphEditorInteractor.addVertex(
                vertex.name,
                vertex.center
            )
        }
    }

    private fun removeVertexFromCanvas(point: Point) {
        var removedVertex: VertexVO? = null
        for (vertex in graphVertex) {
            if (vertex.center.getDistTo(point) <= VertexVO.radius) {
                removedVertex = vertex
                break
            }
        }
        if (removedVertex == null)
            return
        for (vertex in graphEditorInteractor.getGraph()) {
            vertex.getEdges().removeAll { it == removedVertex.name }
        }
        graphEditorInteractor.removeVertex(removedVertex.name)
        graphVertex.remove(removedVertex)
    }

    fun checkVertexPosition(point: Point, canvasHeight: Int, canvasWidth: Int) =
        checkCanvasRange(point, canvasWidth, canvasHeight) &&
                checkOtherVertexPosition(point)


    private fun checkCanvasRange(
        point: Point,
        canvasWidth: Int,
        canvasHeight: Int
    ) =
        point.x in ((0f + VertexVO.radius)..(canvasWidth.toFloat() - VertexVO.radius)) &&
                point.y in ((0f + VertexVO.radius)..(canvasHeight.toFloat() - VertexVO.radius))

    private fun checkOtherVertexPosition(point: Point): Boolean {
        for (vertex in graphVertex) {
            if (point.getDistTo(vertex.center) < 2 * VertexVO.radius)
                return false
        }
        return true

    }
}