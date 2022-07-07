package ui.GraphEditor.GraphCanvas

import androidx.compose.ui.graphics.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import utils.EditorState
import utils.getDistTo
import utils.toOffset

// TODO: 06.07.2022 Добавить interactor
class GraphCanvasViewModel(
    private val editorStateFlow: MutableStateFlow<EditorState>
) {
    private val graphVertex = ArrayList<VertexVO>()
    private val graphEdges = ArrayList<Pair<String, String>>()
    private val font = Font().apply {
        size = 15f
    }
    fun selectPoint(point: Point, height: Int, width: Int): Boolean {
        if(checkVertexPosition(point, height, width)) {
            when(editorStateFlow.value) {
                EditorState.SET_VERTEX -> {
                    // TODO: 06.07.2022 Получение названия вершины...
                    val nameMock = "vertex${graphVertex.size+1}"
                    val vertex = VertexVO(name = nameMock, center = point)
                    graphVertex.add(vertex)
//                drawVertex(canvas, vertex)
                }
            }
            return true
        }
        return false



    }

    fun getGraph() = graphVertex

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

    fun addVertex(name: String, center: Point) {
        graphVertex.add(VertexVO(name, center))
    }
    fun drawGraph(canvas: Canvas, value: List<VertexVO>) {
        for (vertex in value) {
            drawVertex(canvas, vertex)
        }
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

    private fun drawEdge(point1: Point, point2: Point) {
        // TODO: 06.07.2022 Рисование ребра
    }

    private fun clearCanvas(canvas: Canvas, defaultColor: Color) {
//        canvas.clear(defaultColor.toArgb())
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