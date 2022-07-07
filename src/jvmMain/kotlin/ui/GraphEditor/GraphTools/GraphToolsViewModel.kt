package ui.GraphEditor.GraphTools

import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow

class GraphToolsViewModel(private val editorStateFlow: MutableStateFlow<EditorState>) {
    fun addVertexTap() {
        editorStateFlow.value = EditorState.SET_VERTEX
    }

    fun removeVertexTap() {
        editorStateFlow.value = EditorState.REMOVE_VERTEX
    }

    fun addEdgeTap() {
        editorStateFlow.value = EditorState.SET_EDGE_FIRST
    }

    fun removeEdgeTap() {
        editorStateFlow.value = EditorState.REMOVE_EDGE
    }


}