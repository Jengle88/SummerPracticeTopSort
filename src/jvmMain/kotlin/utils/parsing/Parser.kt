package utils.parsing

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.`object`.Graph
import java.io.File

object Parser {
    fun checkGraphForValid(graph: Graph) : Boolean {
        val vertexes = graph.getVertexes()
        val ids:ArrayList<Long> = arrayListOf()
        for (i in 0..vertexes.size-1) {
            ids.add(vertexes[i].getId())
        }
        // проверка на одинаковые id
        if (ids.groupingBy { it }.eachCount().filter { it.value > 1 }.isNotEmpty()) return false
        // проверка на несуществующие рёбра
        for (vertex in vertexes) {
            val edges = vertex.getEdges()
            for (edge in edges) {
                if (edge !in ids) return false
            }
        }
        return true
    }
    fun readDataJSON(filePath: String): Graph {
        val builder = GsonBuilder()
        val gson = builder.create()
        var graph = Graph(arrayListOf())
        val text = File(filePath).readText()
        graph = gson.fromJson(text, graph.javaClass)
        if (graph.getVertexes() == null) {
            graph = Graph(arrayListOf())
        }
        if (!checkGraphForValid(graph)) return Graph(arrayListOf())
        return graph
    }

    fun writeDataJSON(filePath: String, graph: Graph){
        val gson = Gson()
        val text = gson.toJson(graph)
        val file = File(filePath)
        file.writeText(text)
    }
}
