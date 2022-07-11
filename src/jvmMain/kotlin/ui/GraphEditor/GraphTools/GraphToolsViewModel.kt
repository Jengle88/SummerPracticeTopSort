package ui.GraphEditor.GraphTools

import androidx.compose.runtime.MutableState
import utils.GraphToolsState
import kotlinx.coroutines.flow.MutableStateFlow
import models.interactor.AlgorithmInteractorImpl
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState
import utils.algorithm.AlgorithmVisualiser

class GraphToolsViewModel(
    private val enableGraphButtons: MutableState<Boolean>,
    private val enableAlgorithmButtons: MutableState<Boolean>,
    private val algorithmInteractorImpl: AlgorithmInteractorImpl,
    private val graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    private val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>
) {
    fun addVertexTap() {
        graphToolsStateFlow.value = if(graphToolsStateFlow.value == GraphToolsState.SET_VERTEX) {
            enableAlgorithmButtons.value = true
            GraphToolsState.WAITING
        }
        else {
            enableAlgorithmButtons.value = false
            GraphToolsState.SET_VERTEX
        }
    }

    fun removeVertexTap() {
        graphToolsStateFlow.value = if(graphToolsStateFlow.value == GraphToolsState.REMOVE_VERTEX) {
            enableAlgorithmButtons.value = true
            GraphToolsState.WAITING
        }
        else {
            enableAlgorithmButtons.value = false
            GraphToolsState.REMOVE_VERTEX
        }
    }

    fun addEdgeTap() {
        graphToolsStateFlow.value = if(graphToolsStateFlow.value == GraphToolsState.SET_EDGE_FIRST) {
            enableAlgorithmButtons.value = true
            GraphToolsState.WAITING
        }
        else {
            enableAlgorithmButtons.value = false
            GraphToolsState.SET_EDGE_FIRST
        }
    }

    fun removeEdgeTap() {
        graphToolsStateFlow.value = if (graphToolsStateFlow.value == GraphToolsState.REMOVE_EDGE_FIRST) {
            enableAlgorithmButtons.value = true
            GraphToolsState.WAITING
        }
        else {
            enableAlgorithmButtons.value = false
            GraphToolsState.REMOVE_EDGE_FIRST
        }
    }

    fun pauseTap() {
        graphToolsStateFlow.value = GraphToolsState.PAUSE
        currentAlgorithm.value = Pair(currentAlgorithm.value.first, AlgorithmState.PAUSE)
        AlgorithmVisualiser.pauseVisualise()
    }

    fun continueTap() {
        graphToolsStateFlow.value = GraphToolsState.CONTINUE
        currentAlgorithm.value = Pair(currentAlgorithm.value.first, AlgorithmState.IN_PROGRESS_AUTO)
        AlgorithmVisualiser.pauseVisualise()
        AlgorithmVisualiser.startVisualiseTopSortAlgorithm(AlgorithmVisualiser.defaultPeriod)
    }

    fun finishOfVisualisation() {
        graphToolsStateFlow.value = GraphToolsState.TO_FINISH
        AlgorithmVisualiser.toFinish()
    }

    fun beginOfVisualisation() {
        graphToolsStateFlow.value = GraphToolsState.TO_BEGIN
        AlgorithmVisualiser.toBegin()
    }

    fun stepBackTap() {
        graphToolsStateFlow.value = GraphToolsState.STEP_BACK
        currentAlgorithm.value = Pair(currentAlgorithm.value.first, AlgorithmState.IN_PROGRESS_USER)
        AlgorithmVisualiser.pauseVisualise()
        AlgorithmVisualiser.stepBack()
    }

    fun stepNextTap() {
        graphToolsStateFlow.value = GraphToolsState.STEP_NEXT
        currentAlgorithm.value = Pair(currentAlgorithm.value.first, AlgorithmState.IN_PROGRESS_USER)
        AlgorithmVisualiser.pauseVisualise()
        AlgorithmVisualiser.algorithmState = AlgorithmState.IN_PROGRESS_USER
        AlgorithmVisualiser.stepNext()
    }

    fun algTopSortTap() {
        if (!enableGraphButtons.value) {
            enableGraphButtons.value = true
            AlgorithmVisualiser.stopVisualise()
        } else {
            enableGraphButtons.value = false
            AlgorithmVisualiser.pauseVisualise()
            currentAlgorithm.value = Pair(Algorithm.ALG_TOP_SORT, AlgorithmState.START)
            algorithmInteractorImpl.doTopSortAlgorithm()
            currentAlgorithm.value = Pair(Algorithm.ALG_TOP_SORT, AlgorithmState.FINISH)
            AlgorithmVisualiser.startVisualiseTopSortAlgorithm(AlgorithmVisualiser.defaultPeriod)
            notifyAutoProcessingAlgorithm()
        }
    }

    private fun notifyAutoProcessingAlgorithm() {
        currentAlgorithm.value =
            Pair(Algorithm.ALG_TOP_SORT, AlgorithmState.IN_PROGRESS_AUTO)
    }



}