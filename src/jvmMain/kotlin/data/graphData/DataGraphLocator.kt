package data.graphData

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import utils.parsing.Parser

object DataGraphLocator {
    val graphFlow = MutableStateFlow(Graph(arrayListOf()))
    fun readGraphData(path: String): Boolean {
        lateinit var tempGraph: Graph
        try {
            tempGraph = Parser.readDataJSON(path)
        } catch (_: java.lang.Exception) {
            return false
        }
        graphFlow.value = tempGraph
        return true
    }

    fun saveGraphData(path: String) {
        Parser.writeDataJSON(path, graphFlow.value)
    }

}