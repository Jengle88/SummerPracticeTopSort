package ui.GraphEditor.GraphCanvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import data.graphData.DataGraphLocator
import data.repositoryImpl.GraphEditorRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.GraphEditorInteractorImpl
import ui.GraphEditor.AddVertexAlertDialog.AddVertexAlertDialog
import ui.GraphEditor.ShowVertexNameDialog.ShowVertexNameDialogState
import utils.GraphToolsState
import utils.toPoint


@Composable
@Preview
fun GraphCanvas(
    modifier: Modifier = Modifier,
    graphToolsStateFlow: MutableStateFlow<GraphToolsState>
) {
    val graph = remember { mutableStateListOf<VertexVO>() }
    val graphCanvasViewModel = remember {
        GraphCanvasViewModel(
            graphToolsStateFlow,
            GraphEditorInteractorImpl(GraphEditorRepositoryImpl(DataGraphLocator.graphFlow)),
            graph
        )
    }
    val vertexNameForAlertDialog = remember { mutableStateOf("") }
    val addVertexAlertDialogState = AddVertexAlertDialog(graphCanvasViewModel.vertexNameFlow)
    val showVertexNameDialogState = ShowVertexNameDialogState(vertexNameForAlertDialog)
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                this.detectTapGestures(
                    onTap = { offset ->
                        graphCanvasViewModel.selectPoint(
                            addVertexAlertDialogState,
                            offset.toPoint(),
                            this.size.height,
                            this.size.width
                        )
                    },
                    onLongPress = { offset ->
                        graphCanvasViewModel.showVertexName(
                            offset.toPoint(),
                            vertexNameForAlertDialog,
                            showVertexNameDialogState
                        )
                    }
                )
            }
    ) {
        graphCanvasViewModel.drawGraph(this.drawContext.canvas, graph.toList())
    }

}

