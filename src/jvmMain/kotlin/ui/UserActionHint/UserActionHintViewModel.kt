package ui.UserActionHint

import androidx.compose.runtime.MutableState
import data.utils.EditorState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserActionHintViewModel(
) {
    private fun getTitle(editorState: EditorState) = "Пояснение: " + when(editorState) {
            EditorState.WAITING -> "режим ожидания"
            EditorState.SET_VERTEX -> "добавление вершины"
            EditorState.REMOVE_VERTEX -> "удаление вершины"
            EditorState.SET_EDGE_FIRST -> "выбор вершины, откуда будет выходить ребро"
            EditorState.SET_EDGE_SECOND -> "выбор вершины, куда будет входить ребро"
            EditorState.REMOVE_EDGE -> "удаление ребра"
        }
    fun subscribeTitleToEditorState(title: MutableState<String>, editorStateFlow: MutableStateFlow<EditorState>) {
        editorStateFlow.onEach { state ->
            title.value = getTitle(state)
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}