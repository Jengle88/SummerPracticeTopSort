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
import data.utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
@Preview
fun UserActionHint(
    editorStateFlow: MutableStateFlow<EditorState>,
    modifier: Modifier = Modifier
) {
    val title = remember { mutableStateOf("") }
    val userActionHintViewModel = UserActionHintViewModel(title, editorStateFlow)

    Text(
        text = userActionHintViewModel.getTitle(editorStateFlow.value),
        modifier = modifier,
        style = TextStyle(
            color = Color.Gray
        ),
        textAlign = TextAlign.Center

    )

}