package utils.algorithm

data class AlgorithmProtocol(
    private val listOfActions: List<Pair<String, String>>
) {
    fun getCountActions() = listOfActions.size

    fun getListActions(range: IntRange = listOfActions.indices): List<Pair<String, String>> {
        val listActions = mutableListOf<Pair<String, String>>()
        for (index in range) {
            listActions.add(listOfActions[index])
        }
        return listActions
    }
}