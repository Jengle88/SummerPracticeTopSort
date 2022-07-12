package utils

import org.jetbrains.skia.Point
import kotlin.math.pow
import kotlin.math.sqrt

fun Point.getDistTo(other: Point): Double = sqrt(
    (this.x - other.x).toDouble().pow(2) +
            (this.y - other.y).toDouble().pow(2)
)
