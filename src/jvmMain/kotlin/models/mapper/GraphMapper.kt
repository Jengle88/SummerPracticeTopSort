package models.mapper

import androidx.compose.ui.graphics.Color
import data.mock.VertexMock
import ui.GraphEditor.GraphCanvas.VertexVO

fun VertexMock.toVertexVO() = VertexVO(
    name = this.getName(),
    center = this.center,
    color = Color.Black
)

