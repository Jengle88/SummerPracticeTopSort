package ui.GraphEditor.GraphCanvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.toArgb
import data.utils.EditorState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import models.interactor.GraphEditorInteractor
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point

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
    fun selectPoint(point: Point) {
        when(editorStateFlow.value) {
            EditorState.SET_VERTEX -> {
                // TODO: 06.07.2022 Дописать рисование
            }
        }
    }

    fun drawVertex(center: Point) {
        // TODO: 06.07.2022 Рисование вершины
    }

    fun drawEdge(point1: Point, point2: Point) {
        // TODO: 06.07.2022 Рисование ребра
    }

    fun clearCanvas(canvas: Canvas, defaultColor: Color) {
        canvas.clear(defaultColor.toArgb())
    }

    fun getTextOffset(
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