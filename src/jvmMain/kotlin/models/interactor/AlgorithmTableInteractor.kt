package models.interactor

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow

interface AlgorithmTableInteractor {
    fun getGraphTopSortInfo(): Map<String, String>
}

class AlgorithmTableInteractorImpl(private val graphFlow: MutableStateFlow<Graph>): AlgorithmTableInteractor {
    override fun getGraphTopSortInfo(): Map<String, String> {
        return graphFlow.value.getVertexes().associate { vertex ->
            Pair(
                "${vertex.getName()} (id: ${vertex.getId()})",
                "Order: ${vertex.order}"
            )
        }
    }
}