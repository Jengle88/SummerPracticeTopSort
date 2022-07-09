package parsing

import data.dataGraph.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

object Parser {
    fun readData(filePath: String): Graph {
        val builder = GsonBuilder()
        val gson = builder.create()
        var graph = Graph(arrayListOf())
        val text = File(filePath).readText()
        graph = gson.fromJson(text, graph.javaClass)
        return graph
    }

    fun writeData(filePath: String, graph: Graph){
        val gson = Gson()
        val text = gson.toJson(graph)
        val file = File(filePath)
        file.writeText(text)
    }
}
