package models.interactor

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import utils.algorithm.AlgorithmVisualiser
import utils.algorithm.GraphAlgorithm

class AlgorithmInteractorImpl(private val graphFlow: MutableStateFlow<Graph>):
    TableInfoInteractor {
    override fun doTopSortAlgorithm() {
        val (topSortResult, protocol) = GraphAlgorithm.TopSortActions(graphFlow.value)
        for ((vertex, order) in topSortResult) {
            vertex.order = order
        }
        AlgorithmVisualiser.loadResult(
            kindOfVertexResult = { vertex -> "Order: ${vertex.order}" },
            topSortResult = topSortResult,
            protocol = protocol
        )
    }
}