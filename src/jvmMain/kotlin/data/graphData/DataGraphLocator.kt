package data.graphData

import data.`object`.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import utils.parsing.Parser

object DataGraphLocator {
    val graphFlow = MutableStateFlow(Graph(arrayListOf()))
    fun readGraphData(path: String) {
        lateinit var tempGraph: Graph
        try {
            tempGraph = Parser.readDataJSON(path)
        } catch (_: java.lang.Exception) {
            return
        }
        graphFlow.value = tempGraph
    }

    fun saveGraphData(path: String) {
        Parser.writeDataJSON(path, graphFlow.value)
    }

}