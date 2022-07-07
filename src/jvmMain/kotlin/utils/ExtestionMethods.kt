package utils

import androidx.compose.ui.geometry.Offset
import org.jetbrains.skia.Point
import kotlin.math.pow
import kotlin.math.sqrt


fun Offset.toPoint() = Point(this.x, this.y)
fun Point.toOffset() = Offset(this.x, this.y)
fun Point.getDistTo(other: Point): Double = sqrt(
    (this.x - other.x).toDouble().pow(2) +
            (this.y - other.y).toDouble().pow(2)
)
