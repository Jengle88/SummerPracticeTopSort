package ui.GraphEditor.GraphCanvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import data.utils.EditorState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import models.interactor.GraphEditorInteractor
import org.jetbrains.skia.*

enum class GraphCanvasItem {
    VERTEX,
    EDGE
}

// TODO: 06.07.2022 Добавить interactor
class GraphCanvasViewModel(
    private val editorStateFlow: MutableStateFlow<EditorState>,
    private val drawScope: DrawScope
) {
    private val graphVertex = ArrayList<VertexVO>()
    private val graphEdges = ArrayList<Pair<String, String>>()
    private val font = Font().apply {
        size = 15f
    }
    fun selectPoint(point: Point) {
        when(editorStateFlow.value) {
            EditorState.SET_VERTEX -> {
                // TODO: 06.07.2022 Получение названия вершины...
                val nameMock = "vertex${graphVertex.size+1}"
                val vertex = VertexVO(name = nameMock, center = point)
                graphVertex.add(vertex)
                drawVertex(vertex)
            }
        }
    }

    private fun drawVertex(vertex: VertexVO) {
        drawScope.drawCircle(
            color = vertex.color,
            radius = VertexVO.radius,
            center = Offset(vertex.center.x, vertex.center.y),
            style = Stroke()
        )
        val offset = getTextOffset(font, vertex.name)
        drawScope.drawIntoCanvas {
            it.nativeCanvas.drawString(
                s = vertex.name,
                x = vertex.center.x - offset,
                y = vertex.center.y + 5f,
                paint = Paint().apply {
                    this.color = Color.Black.toArgb()
                },
                font = font
            )
        }
    }

    private fun drawEdge(point1: Point, point2: Point) {
        // TODO: 06.07.2022 Рисование ребра
    }

    private fun clearCanvas(canvas: Canvas, defaultColor: Color) {
        canvas.clear(defaultColor.toArgb())
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