package ui.GraphEditor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ui.GraphEditor.GraphCanvas.GraphCanvas
import ui.GraphEditor.GraphTools.GraphTools
import utils.algorithm.Algorithm
import utils.GraphToolsState
import utils.algorithm.AlgorithmState

@Composable
@Preview
fun GraphEditor(
    graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val boxWithConstraintsScope = this
        val graphEditorWidth = 325.dp
        GraphCanvas(
            graphToolsStateFlow = graphToolsStateFlow,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(
                    width = boxWithConstraintsScope.maxWidth - graphEditorWidth,
                    height = boxWithConstraintsScope.minHeight
                )
                .background(
                    color = Color.LightGray
                )
        )
        GraphTools(
            graphToolsStateFlow = graphToolsStateFlow,
            currentAlgorithm = currentAlgorithm,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(graphEditorWidth)
                .height(boxWithConstraintsScope.minHeight)
                .background(Color.White)

        )
    }

}




