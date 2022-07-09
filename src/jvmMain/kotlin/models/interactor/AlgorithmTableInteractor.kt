package models.interactor

import data.`object`.Graph

interface AlgorithmTableInteractor {
    fun getGraphTopSortInfo(): Map<String, String>
}

class AlgorithmTableInteractorImpl(private val graph: Graph): AlgorithmTableInteractor {
    override fun getGraphTopSortInfo(): Map<String, String> {
        return graph.getVertexes().associate { vertex ->
            Pair(
                "${vertex.getName()} (id: ${vertex.getId()})",
                "Order: ${vertex.order}"
            )
        }
    }
}