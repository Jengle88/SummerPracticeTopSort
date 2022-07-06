package ui.GraphEditor

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
import data.utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import ui.GraphEditor.GraphCanvas.GraphCanvas
import ui.GraphEditor.GraphTools.GraphTools

@Composable
@Preview
fun GraphEditor(
    editorStateFlow: MutableStateFlow<EditorState>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val graphEditorWidth = 325.dp
        Box(
            Modifier.border(2.dp, Color.LightGray)
        ) {
            GraphCanvas(
                editorStateFlow = editorStateFlow,
                modifier = Modifier
                .align(Alignment.TopStart)
                .size(800.dp, 700.dp)
                .background(
                    color = Color.LightGray
                )
            )
        }

        GraphTools(
            editorStateFlow = editorStateFlow,
            modifier = Modifier
            .align(Alignment.TopEnd)
            .width(graphEditorWidth)
            .padding(start = 16.dp)
        )
    }

}




