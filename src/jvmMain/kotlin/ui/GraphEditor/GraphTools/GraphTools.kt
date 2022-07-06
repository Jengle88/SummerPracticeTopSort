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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun GraphTools(
    editorStateFlow: MutableStateFlow<EditorState>,
    modifier: Modifier = Modifier
) {
    val graphToolsViewModel = GraphToolsViewModel(editorStateFlow)
    Column(
        modifier = modifier
    ) {
        val buttonModifier = Modifier
            .weight(0.5f)
            .align(Alignment.CenterHorizontally)
        val rowButtonsModifier = Modifier
            .fillMaxWidth()

        EditGraphElements(rowButtonsModifier, buttonModifier, graphToolsViewModel)
        AlgorithmPanel(rowButtonsModifier, buttonModifier, graphToolsViewModel)
        AlgorithmButtons(graphToolsViewModel)
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
            onClick = {}
        ) {
            Image(
                painter = painterResource("drawable/add_vertex.ico"),
                contentDescription = null
            )
        }
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {}
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
            onClick = {}
        ) {
            Image(
                painter = painterResource("drawable/add_edge.ico"),
                contentDescription = null
            )
        }
        IconButton(
            modifier = buttonModifier
                .size(64.dp),
            onClick = {}
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
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.Pause,
                contentDescription = null
            )
        }
        Button(
            modifier = buttonModifier,
            onClick = {}
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
            onClick = {}
        ) {
            Text("<- Шаг")
        }
        Button(
            modifier = buttonModifier,
            onClick = {}
        ) {
            Text("Шаг ->")
        }
    }
}

@Composable
fun AlgorithmButtons(graphToolsViewModel: GraphToolsViewModel) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        text = "Алгоритмы",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {}
    ) {
        Text(
            text = "Топологическая сортировка",
            textAlign = TextAlign.Center
        )
    }
}
