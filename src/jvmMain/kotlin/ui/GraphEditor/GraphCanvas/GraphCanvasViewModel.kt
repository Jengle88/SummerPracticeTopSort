package ui.GraphEditor.GraphCanvas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Canvas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.interactor.GraphEditorInteractor
import models.mapper.toVertexVO
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import utils.CanvasDrawLib
import utils.EditorState
import utils.getDistTo

class GraphCanvasViewModel(
    private val editorStateFlow: MutableStateFlow<EditorState>,
    private val graphEditorInteractor: GraphEditorInteractor,
    private val graphVertex: SnapshotStateList<VertexVO>
) {
    private val font = Font().apply {
        size = 15f
    }
    private var firstVertexForEdge: VertexVO? = null
    val vertexNameFlow = MutableStateFlow("")
    private lateinit var lastPoint: Point
    fun drawGraph(canvas: Canvas, value: List<VertexVO>) {
        val mapVertex: MutableMap<Long, Point> = mutableMapOf()
        for (vertex in value) {
            CanvasDrawLib.drawVertex(canvas, vertex, font)
            mapVertex[vertex.id] = vertex.center
        }
        for (vertex in value) {
            val listOfEdges = graphEditorInteractor.getGraph().getVertex(vertex.id)?.getEdges()
            for (otherVertexId in listOfEdges ?: ArrayList()) {
                CanvasDrawLib.drawEdge(canvas, mapVertex[vertex.id], mapVertex[otherVertexId])
            }
        }
    }

    fun selectPoint(
        addVertexAlertDialogState: MutableState<Boolean>,
        point: Point,
        height: Int,
        width: Int
    ) {
        rememberPoint(point)
        when (editorStateFlow.value) {
            EditorState.SET_VERTEX -> {
                firstVertexForEdge = null
                addVertexAlertDialogState.value = true
                addVertexToCanvas(lastPoint, height, width)
            }
            EditorState.REMOVE_VERTEX -> {
                firstVertexForEdge = null
                removeVertexFromCanvas(lastPoint)
            }
            EditorState.SET_EDGE_FIRST -> {
                setFirstEdgePointAdd(lastPoint)
            }
            EditorState.SET_EDGE_SECOND -> {
                setSecondEdgePointForAdd(lastPoint)
            }
            EditorState.REMOVE_EDGE_FIRST -> {
                setFirstEdgePointForRemove(lastPoint)
            }
            EditorState.REMOVE_EDGE_SECOND -> {
                setSecondEdgePointForRemove(lastPoint)
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
                firstVertexForEdge!!.id,
                secondVertex.id
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
                firstVertexForEdge!!.id,
                secondVertex.id
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
        CoroutineScope(Dispatchers.Main).launch {
            vertexNameFlow.collect {
                if (it != "") {
                    if (checkVertexPosition(point, height, width)) {
                        val name = it
                        vertexNameFlow.value = ""
                        val vertex = graphEditorInteractor.addVertex(
                            name,
                            point
                        ).toVertexVO()
                        graphVertex.add(vertex)
                    }
                }
            }
        }
    }

    private fun removeVertexFromCanvas(point: Point) {
        var removedVertex: VertexVO? = null
        var indexOfRemoveVertex = 0
        for ((index, vertex) in graphVertex.withIndex()) {
            if (vertex.center.getDistTo(point) <= VertexVO.radius) {
                removedVertex = vertex
                indexOfRemoveVertex = index
                break
            }
        }
        if (removedVertex == null)
            return
        for (vertex in graphEditorInteractor.getGraph().getVertexes()) {
            vertex.getEdges().removeAll { it == removedVertex.id }
        }
        graphEditorInteractor.removeVertex(removedVertex.id)
        graphVertex.removeAt(indexOfRemoveVertex)
    }

    fun checkVertexPosition(point: Point?, canvasHeight: Int, canvasWidth: Int) =
        point != null && checkCanvasRange(point, canvasWidth, canvasHeight) &&
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

    private fun rememberPoint(point: Point) {
        lastPoint = point
    }
}