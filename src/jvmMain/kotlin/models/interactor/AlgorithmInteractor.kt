package models.interactor

import data.`object`.Graph
import utils.algorithm.GraphAlgorithm

interface AlgorithmInteractor {
    fun startTopSortAlgorithm()
}

class AlgorithmInteractorImpl(private val graph: Graph): AlgorithmInteractor {
    override fun startTopSortAlgorithm() {
        val topSortResult = GraphAlgorithm.TopSort(graph)
        for ((vertex, order) in topSortResult) {
            vertex.order = order
        }
    }
}