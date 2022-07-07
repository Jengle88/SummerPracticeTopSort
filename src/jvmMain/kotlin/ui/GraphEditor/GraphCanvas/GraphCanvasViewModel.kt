package ui.GraphEditor.GraphCanvas

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.GraphEditorInteractor
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import utils.EditorState
import utils.getDistTo
import utils.toOffset
import kotlin.math.abs
import kotlin.math.sin

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
            drawVertex(canvas, vertex)
            mapVertex[vertex.name] = vertex.center
        }
        for (vertex in value) {
            val listOfEdges = graphEditorInteractor.getGraph().find { it.getName() == vertex.name }
            for (otherVertex in listOfEdges?.getEdges() ?: ArrayList()) {
                drawEdge(canvas, mapVertex[vertex.name], mapVertex[otherVertex])
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
        }
        firstVertexForEdge = null
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
        }
        firstVertexForEdge = null
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

    private fun drawVertex(canvas: Canvas, vertex: VertexVO) {
        canvas.drawCircle(
            radius = VertexVO.radius,
            center = vertex.center.toOffset(),
            paint = Paint().apply {
                this.color = vertex.color
                this.style = PaintingStyle.Stroke
            }
        )
        val offset = getTextOffset(font, vertex.name)
        drawVertexTitle(canvas, vertex, offset)
    }

    private fun drawEdge(canvas: Canvas, point1: Point?, point2: Point?) {
        if (point1 == null || point2 == null)
            return
        val (newPoint1, newPoint2) = recalcPointsEdge(point1, point2)
        // основная линия
        canvas.drawLine(
            Offset(newPoint1.x, newPoint1.y),
            Offset(newPoint2.x, newPoint2.y),
            Paint().apply {
                color = Color.Black
            }
        )
        // FIXME: 07.07.2022 Исправить отрисовку стрелки
        drawArrowForEdge(newPoint1, newPoint2, canvas)
    }

    private fun drawArrowForEdge(
        newPoint1: Point,
        newPoint2: Point,
        canvas: Canvas
    ) {
        // часть стрелки
        var deltaY = VertexVO.radius * sin(Math.PI / 4)
        var deltaX = VertexVO.radius - deltaY
        var newPointForArrow = Offset(
            newPoint2.x - deltaX.toFloat(),
            newPoint2.y - deltaY.toFloat()
        )
        canvas.drawLine(
            newPoint2.toOffset(),
            newPointForArrow,
            Paint().apply {
                color = Color.Black
            }
        )
        // часть стрелки
        deltaY = VertexVO.radius * sin(-Math.PI / 4)
        deltaX = VertexVO.radius - deltaY
        newPointForArrow = Offset(
            newPoint2.x + deltaX.toFloat(),
            newPoint2.y + deltaY.toFloat()
        )
        canvas.drawLine(
            newPoint2.toOffset(),
            newPointForArrow,
            Paint().apply {
                color = Color.Black
            }
        )
    }

    private fun recalcPointsEdge(point1: Point, point2: Point): Pair<Point, Point> {
        val R = point1.getDistTo(point2)
        val r = VertexVO.radius
        val DeltaX = abs(point1.x - point2.x)
        val DeltaY = abs(point1.y - point2.y)
        val deltaX = r / R * DeltaX
        val deltaY = r / R * DeltaY
        val sign1X = if (point1.x < point2.x) 1 else -1
        val sign2X = sign1X * -1
        val sign1Y = if (point1.y < point2.y) 1 else -1
        val sign2Y = sign1Y * -1
        return Pair(
            Point(point1.x + (deltaX * sign1X).toFloat(), point1.y + (deltaY * sign1Y).toFloat()),
            Point(point2.x + (deltaX * sign2X).toFloat(), point2.y + (deltaY * sign2Y).toFloat())
        )
    }

    private fun drawVertexTitle(
        canvas: Canvas,
        vertex: VertexVO,
        offset: Int
    ) {
        canvas.nativeCanvas.drawString(
            s = vertex.name,
            x = vertex.center.x - offset,
            y = vertex.center.y + 5f,
            paint = org.jetbrains.skia.Paint().apply {
                color = Color.Black.toArgb()
            },
            font = font
        )
    }


    private fun getTextOffset(
        font: Font,
        textLabel: String
    ): Int {
        val textWidth = font.getWidths(font.getStringGlyphs(textLabel))
        var textOffset = 0
        for (i in 0 until textWidth.size / 2) {
            textOffset += textWidth[i].toInt()
        }
        if (textWidth.size % 2 == 1) {
            textOffset += textWidth[textWidth.size / 2].toInt() / 2
        }
        return textOffset
    }
}