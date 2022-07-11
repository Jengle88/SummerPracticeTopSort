package ui.UserActionHint

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import utils.GraphToolsState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun UserActionHint(
    graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    modifier: Modifier = Modifier
) {
    val title = remember { mutableStateOf("") }
    val userActionHintViewModel = remember { mutableStateOf(UserActionHintViewModel()) }
    userActionHintViewModel.value.subscribeTitleToEditorState(title, graphToolsStateFlow)

    Text(
        text = title.value,
        modifier = modifier,
        style = TextStyle(
            color = Color.Gray
        ),
        textAlign = TextAlign.Center

    )

}