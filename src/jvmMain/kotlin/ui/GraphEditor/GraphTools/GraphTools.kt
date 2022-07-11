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
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.graphData.DataGraphLocator
import utils.GraphToolsState
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.AlgorithmInteractorImpl
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

@Composable
@Preview
fun GraphTools(
    graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    modifier: Modifier = Modifier
) {
    val enableGraphButtons = remember { mutableStateOf(true) }
    val enableAlgorithmButtons = remember { mutableStateOf(true) }
    val graphToolsViewModel = remember { mutableStateOf(GraphToolsViewModel(
        enableGraphButtons,
        enableAlgorithmButtons,
        AlgorithmInteractorImpl(DataGraphLocator.graphFlow),
        graphToolsStateFlow,
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
        EditGraphElements(rowButtonsModifier, buttonModifier, graphToolsViewModel.value, enableGraphButtons)
        AlgorithmPanel(rowButtonsModifier, buttonModifier, graphToolsViewModel.value, enableAlgorithmButtons)
        AlgorithmButtons(rowButtonsModifier, graphToolsViewModel.value, enableAlgorithmButtons)
    }
}

@Composable
fun EditGraphElements(
    rowButtonsModifier: Modifier,
    buttonModifier: Modifier,
    graphToolsViewModel: GraphToolsViewModel,
    enableGraphButtons: MutableState<Boolean>
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
            enabled = enableGraphButtons.value,
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
            enabled = enableGraphButtons.value,
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
            enabled = enableGraphButtons.value,
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
            enabled = enableGraphButtons.value,
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
    graphToolsViewModel: GraphToolsViewModel,
    enableAlgorithmButtons: MutableState<Boolean>
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
            enabled = enableAlgorithmButtons.value,
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
            enabled = enableAlgorithmButtons.value,
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
            enabled = enableAlgorithmButtons.value,
            onClick = {
                graphToolsViewModel.beginOfVisualisation()
            }
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = null
            )
        }
        Button(
            modifier = buttonModifier,
            enabled = enableAlgorithmButtons.value,
            onClick = {
                graphToolsViewModel.finishOfVisualisation()
            }
        ) {
            Icon(
                imageVector = Icons.Default.SkipNext,
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
            enabled = enableAlgorithmButtons.value,
            onClick = {
                graphToolsViewModel.stepBackTap()
            }
        ) {
            Text("<- Шаг")
        }
        Button(
            modifier = buttonModifier,
            enabled = enableAlgorithmButtons.value,
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
    graphToolsViewModel: GraphToolsViewModel,
    enableAlgorithmButtons: MutableState<Boolean>
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
            enabled = enableAlgorithmButtons.value,
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
