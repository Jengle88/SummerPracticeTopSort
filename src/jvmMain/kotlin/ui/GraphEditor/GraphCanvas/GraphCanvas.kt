package ui.GraphEditor.GraphCanvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import data.repositoryImpl.MockGraphDataStorageInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import utils.EditorState
import utils.toPoint


@Composable
@Preview
fun GraphCanvas(
    modifier: Modifier = Modifier,
    editorStateFlow: MutableStateFlow<EditorState>
) {
    val graph = remember { mutableStateListOf<VertexVO>() }
    val graphCanvasViewModel = GraphCanvasViewModel(
        editorStateFlow,
        MockGraphDataStorageInteractor,
        graph
    )
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                this.detectTapGestures(onTap = { offset ->
                    graphCanvasViewModel.selectPoint(
                        offset.toPoint(),
                        this.size.height,
                        this.size.width
                    )
                })
            }
    ) {
        graphCanvasViewModel.drawGraph(this.drawContext.canvas, graph.toList())
    }
}

