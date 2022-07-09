package models.interactor

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import utils.algorithm.GraphAlgorithm

interface AlgorithmInteractor {
    fun startTopSortAlgorithm()
}

class AlgorithmInteractorImpl(private val graphFlow: MutableStateFlow<Graph>): AlgorithmInteractor {
    override fun startTopSortAlgorithm() {
        val topSortResult = GraphAlgorithm.TopSort(graphFlow.value)
        for ((vertex, order) in topSortResult) {
            vertex.order = order
        }
    }
}