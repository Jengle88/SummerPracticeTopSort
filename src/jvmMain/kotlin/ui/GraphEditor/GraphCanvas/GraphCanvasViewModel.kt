package ui.GraphEditor.GraphCanvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.interactor.GraphEditorInteractor
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point


class GraphCanvasViewModel(private val graphEditorInteractor: GraphEditorInteractor) {
    private var graphData: Flow<ArrayList<VertexVO>> = flow {
//        emit(graphEditorInteractor.getGraph())
        // TODO: 06.07.2022 Дописать про загрузку
    }
//    private var graphData: Flow<ArrayList<VertexVO>> = flow {
//        emit(graphCanvasInteractor.getGraph())
//    }
//
//
//    fun getGraphData() = graphData
//    fun updateGraph() {
//        graphCanvasInteractor.addVertex("vertex", Point(1f, 5f))
//    }

    fun selectPoint(point: Point) {
        // TODO: 06.07.2022 Передача информации наверх и ожидание действия
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