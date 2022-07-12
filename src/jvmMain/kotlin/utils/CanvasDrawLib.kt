package utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import models.mapper.toOffset
import org.jetbrains.skia.Font
import org.jetbrains.skia.Point
import ui.GraphEditor.GraphCanvas.VertexVO
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

object CanvasDrawLib {

    fun drawVertex(canvas: Canvas, vertex: VertexVO, font: Font) {
        canvas.drawCircle(
            radius = VertexVO.radius,
            center = vertex.center.toOffset(),
            paint = Paint().apply {
                this.color = vertex.color
                this.style = PaintingStyle.Stroke
            }
        )
        val title = if(vertex.name.length > VertexVO.cntLetters) vertex.name.substring(0, VertexVO.cntLetters) + "…" else vertex.name
        val offset = getTextOffset(font, title)
        drawVertexTitle(canvas, title, vertex, offset, font)
    }

    fun drawEdge(canvas: Canvas, point1: Point?, point2: Point?) {
        if (point1 == null || point2 == null || point1.getDistTo(point2) < 2 * VertexVO.radius)
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
        // добавление стрелок
        drawArrowForEdge(newPoint1, newPoint2, canvas)
    }

    private fun drawArrowForEdge(
        newPoint1: Point,
        newPoint2: Point,
        canvas: Canvas,
        angle: Float = 35f
    ) {
        val r = VertexVO.radius
        val dist = newPoint1.getDistTo(newPoint2)
        val (sign1X, sign1Y, sign2X, sign2Y) = getPointSign(newPoint1, newPoint2)
        val newPoint1ForArrow = Point(
            newPoint2.x + sign2X * (r / dist * abs(newPoint1.x - newPoint2.x)).toFloat(),
            newPoint2.y + sign2Y * (r / dist * abs(newPoint1.y - newPoint2.y)).toFloat(),
        )
        // первая часть стрелки
        var newPointForArrow =
            getArrowPosition(newPoint2, newPoint1ForArrow, angle)
        canvas.drawLine(
            newPoint2.toOffset(),
            newPointForArrow,
            Paint().apply {
                color = Color.Black
            }
        )
        // вторая часть стрелки
        newPointForArrow = getArrowPosition(newPoint2, newPoint1ForArrow, -angle)
        canvas.drawLine(
            newPoint2.toOffset(),
            newPointForArrow,
            Paint().apply {
                color = Color.Black
            }
        )
    }

    private fun getPointSign(
        newPoint1: Point,
        newPoint2: Point
    ): List<Int> {
        val sign1X = if (newPoint1.x < newPoint2.x) 1 else -1
        val sign2X = sign1X * -1
        val sign1Y = if (newPoint1.y < newPoint2.y) 1 else -1
        val sign2Y = sign1Y * -1
        return listOf(sign1X, sign1Y, sign2X, sign2Y)
    }

    private fun getArrowPosition(
        newPoint2: Point,
        newPoint1ForArrow: Point,
        angle: Float
    ): Offset {
        // вектор на прямой
        val vector = Point(
            newPoint2.x - newPoint1ForArrow.x,
            newPoint2.y - newPoint1ForArrow.y
        )
        // повёрнутый вектор
        val rotatedVector = Point(
            vector.x * cos(Math.PI / 180 * angle).toFloat() + vector.y * sin(Math.PI / 180 * angle).toFloat(),
            vector.y * cos(Math.PI / 180 * angle).toFloat() - vector.x * sin(Math.PI / 180 * angle).toFloat()
        )
        return Offset(
            newPoint2.x - rotatedVector.x,
            newPoint2.y - rotatedVector.y,
        )
    }

    private fun recalcPointsEdge(point1: Point, point2: Point): Pair<Point, Point> {
        val R = point1.getDistTo(point2)
        val r = VertexVO.radius
        val DeltaX = abs(point1.x - point2.x)
        val DeltaY = abs(point1.y - point2.y)
        val deltaX = r / R * DeltaX
        val deltaY = r / R * DeltaY
        val (sign1X, sign1Y, sign2X, sign2Y) = getPointSign(point1, point2)
        return Pair(
            Point(point1.x + (deltaX * sign1X).toFloat(), point1.y + (deltaY * sign1Y).toFloat()),
            Point(point2.x + (deltaX * sign2X).toFloat(), point2.y + (deltaY * sign2Y).toFloat())
        )
    }

    private fun drawVertexTitle(
        canvas: Canvas,
        title: String,
        vertex: VertexVO,
        offset: Int,
        font: Font
    ) {
        canvas.nativeCanvas.drawString(
            s = title,
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