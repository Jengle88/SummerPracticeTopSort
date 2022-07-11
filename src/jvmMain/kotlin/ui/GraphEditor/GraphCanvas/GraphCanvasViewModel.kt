package ui.GraphEditor.GraphCanvas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import models.interactor.GraphEditorInteractor
import models.mapper.toVertexVO
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import utils.CanvasDrawLib
import utils.GraphToolsState
import utils.getDistTo

class GraphCanvasViewModel(
    private val graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    private val graphEditorInteractor: GraphEditorInteractor,
    private val graphVertex: SnapshotStateList<VertexVO>
) {
    private val font = Font().apply {
        size = 15f
    }
    private var firstVertexForEdge: VertexVO? = null
    val vertexNameFlow = MutableStateFlow("")
    private lateinit var lastPoint: Point

    init {
        graphListener()
        editorStateListener()
    }

    private fun graphListener() {
        CoroutineScope(Dispatchers.Main).launch {
            graphEditorInteractor.getGraph().collect { graph ->
                graphVertex.clear()
                graphVertex.addAll(graph.getVertexes().map { it.toVertexVO() })
                firstVertexForEdge = null
            }
        }
    }

    private fun editorStateListener() {
        CoroutineScope(Dispatchers.Main).launch {
            graphToolsStateFlow.collectLatest { state ->
                if ("_SECOND" !in state.name) {
                    firstVertexForEdge?.color = Color.Black
                    firstVertexForEdge = null
                }
            }
        }
    }

    fun drawGraph(canvas: Canvas, value: List<VertexVO>) {
        val mapVertex: MutableMap<Long, Point> = mutableMapOf()
        for (vertex in value) {
            CanvasDrawLib.drawVertex(canvas, vertex, font)
            mapVertex[vertex.id] = vertex.center
        }
        for (vertex in value) {
            val listOfEdges = graphEditorInteractor.getGraph().value.getVertex(vertex.id)?.getEdges()
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
        when (graphToolsStateFlow.value) {
            GraphToolsState.SET_VERTEX -> {
                firstVertexForEdge = null
                addVertexAlertDialogState.value = true
                addVertexToCanvas(height, width)
            }
            GraphToolsState.REMOVE_VERTEX -> {
                firstVertexForEdge = null
                removeVertexFromCanvas(lastPoint)
            }
            GraphToolsState.SET_EDGE_FIRST -> {
                setFirstEdgePointAdd(lastPoint)
            }
            GraphToolsState.SET_EDGE_SECOND -> {
                setSecondEdgePointForAdd(lastPoint)
            }
            GraphToolsState.REMOVE_EDGE_FIRST -> {
                setFirstEdgePointForRemove(lastPoint)
            }
            GraphToolsState.REMOVE_EDGE_SECOND -> {
                setSecondEdgePointForRemove(lastPoint)
            }
            else -> {}
        }

    }


    private fun setFirstEdgePointAdd(point: Point) {
        firstVertexForEdge =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null) {
            firstVertexForEdge?.color = Color.Yellow
            graphToolsStateFlow.value = GraphToolsState.SET_EDGE_SECOND
        }
    }

    private fun setSecondEdgePointForAdd(point: Point) {
        val secondVertex =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null && secondVertex != null) {
            graphEditorInteractor.linkVertexes(
                firstVertexForEdge!!.id,
                secondVertex.id
            )
            graphToolsStateFlow.value = GraphToolsState.SET_EDGE_FIRST
            // уведомляем об изменении графа
            graphVertex[graphVertex.lastIndex] = graphVertex.last()
            firstVertexForEdge?.color = Color.Black
            firstVertexForEdge = null
        }
    }

    private fun setFirstEdgePointForRemove(point: Point) {
        firstVertexForEdge =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null) {
            firstVertexForEdge?.color = Color.Red
            graphToolsStateFlow.value = GraphToolsState.REMOVE_EDGE_SECOND
        }
    }

    private fun setSecondEdgePointForRemove(point: Point) {
        val secondVertex =
            graphVertex.find { it.center.getDistTo(point) <= VertexVO.radius }
        if (firstVertexForEdge != null && secondVertex != null) {
            graphEditorInteractor.removeLink(
                firstVertexForEdge!!.id,
                secondVertex.id
            )
            graphToolsStateFlow.value = GraphToolsState.REMOVE_EDGE_FIRST
            // уведомляем об изменении графа
            graphVertex[graphVertex.lastIndex] = graphVertex.last()
            firstVertexForEdge?.color = Color.Black
            firstVertexForEdge = null
        }
    }

    private fun addVertexToCanvas(
        height: Int,
        width: Int
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            vertexNameFlow.collectLatest {
                if (it != "") {
                    if (checkVertexPosition(lastPoint, height, width)) {
                        val name = it
                        val vertex = graphEditorInteractor.addVertex(
                            name,
                            lastPoint
                        ).toVertexVO()
                        graphVertex.add(vertex)
                    }
                    vertexNameFlow.value = ""
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
        for (vertex in graphEditorInteractor.getGraph().value.getVertexes()) {
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