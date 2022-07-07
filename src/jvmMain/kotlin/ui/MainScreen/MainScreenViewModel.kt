package ui.MainScreen

import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow

class MainScreenViewModel {
    val editorState: MutableStateFlow<EditorState> = MutableStateFlow(EditorState.WAITING)
}