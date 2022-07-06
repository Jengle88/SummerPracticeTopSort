package ui.MainScreen

import data.utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow

class MainScreenViewModel {
    val editorState: MutableStateFlow<EditorState> = MutableStateFlow(EditorState.WAITING)
}