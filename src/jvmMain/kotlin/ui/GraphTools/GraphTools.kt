package ui.GraphTools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.GraphTools.GraphCanvas.GraphCanvas
import ui.GraphTools.GraphEditor.GraphEditor

@Composable
@Preview
fun GraphTools(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        val graphEditorWidth = 325.dp
        Box(
            Modifier.border(2.dp, Color.LightGray)
        ) {
            GraphCanvas(Modifier
                .align(Alignment.TopStart)
                .size(800.dp, 700.dp)
                .background(
                    color = Color.LightGray
                )
            )
        }

        GraphEditor(Modifier
            .align(Alignment.TopEnd)
            .width(graphEditorWidth)
            .padding(start = 16.dp)
        )
    }

}




