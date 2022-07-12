package models.mapper

import androidx.compose.ui.geometry.Offset
import org.jetbrains.skia.Point

fun Offset.toPoint() = Point(this.x, this.y)
fun Point.toOffset() = Offset(this.x, this.y)