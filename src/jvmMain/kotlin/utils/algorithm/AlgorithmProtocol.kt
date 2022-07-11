package utils.algorithm

import data.`object`.Vertex

data class AlgorithmProtocol(
    private val kindOfVertexResult: (Vertex) -> String,
    private val listOfActions: List<Pair<String, String>>
) {
    fun getCountActions() = listOfActions.size

    fun getListActions(range: IntRange = listOfActions.indices): List<Pair<String, String>> {
        val listActions = mutableListOf<Pair<String, String>>()
        for (index in range) {
            listActions.add(
                "$index) time = ${listOfActions[index].first}ms" to listOfActions[index].second
            )
        }
        return listActions
    }
}