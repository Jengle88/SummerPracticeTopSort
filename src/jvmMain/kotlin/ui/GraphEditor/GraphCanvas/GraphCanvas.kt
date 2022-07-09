package ui.GraphEditor.GraphCanvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import data.repositoryImpl.GraphDataStorageInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import ui.GraphEditor.AddVertexAlertDialog
import utils.EditorState
import utils.toPoint


@Composable
@Preview
fun GraphCanvas(
    modifier: Modifier = Modifier,
    editorStateFlow: MutableStateFlow<EditorState>
) {
    val graph = remember { mutableStateListOf<VertexVO>() }
    val graphCanvasViewModel = remember {
        GraphCanvasViewModel(
            editorStateFlow,
            GraphDataStorageInteractor,
            graph
        )
    }
    val addVertexAlertDialogState = AddVertexAlertDialog(graphCanvasViewModel.vertexNameFlow)
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                this.detectTapGestures(onTap = { offset ->
                    graphCanvasViewModel.selectPoint(
                        addVertexAlertDialogState,
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

