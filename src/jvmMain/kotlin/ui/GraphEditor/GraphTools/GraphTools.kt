package ui.GraphEditor.GraphTools

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.graphData.DataGraphLocator
import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.AlgorithmInteractorImpl
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

@Composable
@Preview
fun GraphTools(
    editorStateFlow: MutableStateFlow<EditorState>,
    currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    modifier: Modifier = Modifier
) {
    val graphToolsViewModel = remember { mutableStateOf(GraphToolsViewModel(
        AlgorithmInteractorImpl(DataGraphLocator.graph),
        editorStateFlow,
        currentAlgorithm
    )) }
    Column(
        modifier = modifier
    ) {
        val buttonModifier = Modifier
            .weight(0.5f)
            .align(Alignment.CenterHorizontally)
        val rowButtonsModifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp)
        EditGraphElements(rowButtonsModifier, buttonModifier, graphToolsViewModel.value)
        AlgorithmPanel(rowButtonsModifier, buttonModifier, graphToolsViewModel.value)
        AlgorithmButtons(rowButtonsModifier, graphToolsViewModel.value)
    }
}

@Composable
fun EditGraphElements(
    rowButtonsModifier: Modifier,
    buttonModifier: Modifier,
    graphToolsViewModel: GraphToolsViewModel
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        text = "Добавить элементы",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Row(
        modifier = rowButtonsModifier
    ) {
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {
                graphToolsViewModel.addVertexTap()
            }
        ) {
            Image(
                painter = painterResource("drawable/add_vertex.ico"),
                contentDescription = null
            )
        }
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {
                graphToolsViewModel.removeVertexTap()
            }
        ) {
            Image(
                painter = painterResource("drawable/remove_vertex.ico"),
                contentDescription = null
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    Row(
        modifier = rowButtonsModifier
    ) {
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {
                graphToolsViewModel.addEdgeTap()
            }
        ) {
            Image(
                painter = painterResource("drawable/add_edge.ico"),
                contentDescription = null
            )
        }
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {
                graphToolsViewModel.removeEdgeTap()
            }
        ) {
            Image(
                painter = painterResource("drawable/remove_edge.ico"),
                contentDescription = null
            )
        }
    }
}
@Composable
fun AlgorithmPanel(
    rowButtonsModifier: Modifier,
    buttonModifier: Modifier,
    graphToolsViewModel: GraphToolsViewModel
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        text = "Панель управления",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Row(
        modifier = rowButtonsModifier
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = buttonModifier,
            onClick = {
                graphToolsViewModel.pauseTap()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = null
            )
        }
        Button(
            modifier = buttonModifier,
            onClick = {
                graphToolsViewModel.continueTap()
            }
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null
            )
        }
    }
    Row(
        modifier = rowButtonsModifier
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = buttonModifier,
            onClick = {
                graphToolsViewModel.stepBackTap()
            }
        ) {
            Text("<- Шаг")
        }
        Button(
            modifier = buttonModifier,
            onClick = {
                graphToolsViewModel.stepNextTap()
            }
        ) {
            Text("Шаг ->")
        }
    }
}

@Composable
fun AlgorithmButtons(
    rowButtonsModifier: Modifier,
    graphToolsViewModel: GraphToolsViewModel
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        text = "Алгоритмы",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Row(
        modifier = rowButtonsModifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                graphToolsViewModel.algTopSortTap()
            }
        ) {
            Text(
                text = "Топологическая сортировка",
                textAlign = TextAlign.Center
            )
        }
    }
}
