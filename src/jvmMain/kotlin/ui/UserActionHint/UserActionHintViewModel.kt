package ui.UserActionHint

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utils.EditorState

class UserActionHintViewModel(
) {
    private fun getTitle(editorState: EditorState): String =
        "Пояснение: " + when (editorState) {
            EditorState.WAITING -> "режим ожидания"
            EditorState.SET_VERTEX -> "добавление вершины"
            EditorState.REMOVE_VERTEX -> "удаление вершины"
            EditorState.SET_EDGE_FIRST -> "выбор вершины, откуда будет выходить ребро"
            EditorState.SET_EDGE_SECOND -> "выбор вершины, куда будет входить ребро"
            EditorState.REMOVE_EDGE_FIRST -> "удаление ребра, выбор первой вершины"
            EditorState.REMOVE_EDGE_SECOND -> "удаление ребра, выбор второй вершины"
        }
    fun subscribeTitleToEditorState(title: MutableState<String>, editorStateFlow: MutableStateFlow<EditorState>) {
        CoroutineScope(Dispatchers.Main).launch {
            editorStateFlow.collect { state ->
                title.value = getTitle(state)
            }
        }

    }
}