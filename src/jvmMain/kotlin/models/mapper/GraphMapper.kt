package models.mapper

import androidx.compose.ui.graphics.Color
import data.`object`.Vertex
import ui.GraphEditor.GraphCanvas.VertexVO

fun Vertex.toVertexVO() = VertexVO(
    id = this.getId(),
    name = this.getName(),
    center = this.getCenter(),
    color = Color.Black
)

