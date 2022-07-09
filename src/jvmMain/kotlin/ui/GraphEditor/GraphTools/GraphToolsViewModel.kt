package ui.GraphEditor.GraphTools

import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.AlgorithmInteractorImpl
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class GraphToolsViewModel(
    val algorithmInteractorImpl: AlgorithmInteractorImpl,
    private val editorStateFlow: MutableStateFlow<EditorState>,
    private val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>
) {
    fun addVertexTap() {
        editorStateFlow.value = if(editorStateFlow.value == EditorState.SET_VERTEX)
            EditorState.WAITING
        else
            EditorState.SET_VERTEX
    }

    fun removeVertexTap() {
        editorStateFlow.value = if(editorStateFlow.value == EditorState.REMOVE_VERTEX)
            EditorState.WAITING
        else
            EditorState.REMOVE_VERTEX
    }

    fun addEdgeTap() {
        editorStateFlow.value = if(editorStateFlow.value == EditorState.SET_EDGE_FIRST)
            EditorState.WAITING
        else
            EditorState.SET_EDGE_FIRST
    }

    fun removeEdgeTap() {
        editorStateFlow.value = if (editorStateFlow.value == EditorState.REMOVE_EDGE_FIRST)
            EditorState.WAITING
        else
            EditorState.REMOVE_EDGE_FIRST
    }

    fun pauseTap() {
//        editorStateFlow.value = EditorState.PAUSE
    }

    fun continueTap() {
//        editorStateFlow.value = if(editorStateFlow.value == EditorState.CONTINUE)
//            EditorState.ALG_FINISH
//        else
//            EditorState.ALG_TOP_SORT
    }

    fun stepBackTap() {
//        editorStateFlow.value = EditorState.STEP_BACK
    }

    fun stepNextTap() {
//        editorStateFlow.value = EditorState.STEP_NEXT
    }

    fun algTopSortTap() {
        currentAlgorithm.value = Pair(Algorithm.ALG_TOP_SORT, AlgorithmState.START)
        algorithmInteractorImpl.startTopSortAlgorithm()
        currentAlgorithm.value = Pair(Algorithm.ALG_TOP_SORT, AlgorithmState.FINISH)
    }

}