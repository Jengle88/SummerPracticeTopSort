package ui.GraphEditor.GraphCanvas

import androidx.compose.ui.graphics.Color
import org.jetbrains.skia.Point

data class VertexVO(
    val id: Long,
    val name: String,
    val center: Point,
    var color: Color = Color.Black
) {

    companion object {
        const val radius = 25f
        const val cntLetters = 5
    }
}