package ui.UserActionHint

import androidx.compose.runtime.MutableState
import data.utils.EditorState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserActionHintViewModel(
    title: MutableState<String>,
    editorStateFlow: MutableStateFlow<EditorState>
) {
    fun getTitle(editorState: EditorState) = when(editorState) {
            else -> ""
        }
    init {
        CoroutineScope(Dispatchers.Main).launch {
            editorStateFlow.collect { data ->
                title.value = getTitle(data)
            }
        }

    }
}