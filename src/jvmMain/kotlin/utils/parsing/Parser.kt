package utils.parsing

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import data.`object`.Graph
import data.`object`.Vertex
import org.jetbrains.skia.Point
import ui.GraphEditor.GraphCanvas.VertexVO
import utils.getDistTo
import java.io.File

object Parser {
    fun writeDataJSON(filePath: String, graph: Graph){
        val gson = Gson()
        val text = gson.toJson(graph)
        val file = File(filePath)
        file.writeText(text)
    }

    fun readDataJSON(filePath: String): Graph {
        val builder = GsonBuilder()
        val gson = builder.create()
        var graph = Graph(arrayListOf())
        val text = File(filePath).readText()
        graph = gson.fromJson(text, graph.javaClass)
        if (!checkValidVertexes(graph)) throw Exception()
        if (!checkGraphForValid(graph)) throw Exception()
        return graph
    }

    private fun checkValidVertexes(graph: Graph?): Boolean {
        if (graph?.getVertexes() == null) {
            return false
        }
        for (vertex in graph.getVertexes()) {
            if (vertex.getId() == null ||
                    vertex.getName() == null ||
                    vertex.getCenter() == null ||
                    vertex.getEdges() == null) {
                return false
            }
        }
        return true
    }

    fun checkGraphForValid(graph: Graph) : Boolean {
        // проверка на одинаковые id
        if (!checkDifferentIds(graph.getVertexes())) return false

        // проверка на несуществующие рёбра
        if (!checkAvailableVertexes(graph.getVertexes())) return false

        // проверка на неотрицательность координат
        if(!checkAllCoordsGreaterZeroAndRadius(graph.getVertexes())) return false

        // проверка на то, что вершины не перекрывают друг друга
        if (!checkNonIntersectionOfVertexes(graph.getVertexes())) return false

        return true
    }

    private fun checkDifferentIds(vertexes: ArrayList<Vertex>): Boolean {
        val ids: ArrayList<Long> = getIds(vertexes)
        if (ids.toSet().size != ids.size) return false
        return true
    }

    private fun checkAvailableVertexes(vertexes: ArrayList<Vertex>): Boolean {
        val ids: ArrayList<Long> = getIds(vertexes)
        for (vertex in vertexes) {
            val edges = vertex.getEdges()
            for (edge in edges) {
                if (edge !in ids) return false
            }
        }
        return true
    }

    private fun checkAllCoordsGreaterZeroAndRadius(vertexes: ArrayList<Vertex>): Boolean {
        val centers: ArrayList<Point> = getCenters(vertexes)
        for (center in centers) {
            if (center.x <= VertexVO.radius || center.y <= VertexVO.radius) return false
        }
        return true
    }

    private fun checkNonIntersectionOfVertexes(vertexes: ArrayList<Vertex>): Boolean {
        val centers: ArrayList<Point> = getCenters(vertexes)
        for ((i, centerSrc) in centers.withIndex()) {
            for (j in i + 1 until centers.size) {
                if (i != j) {
                    if (centerSrc.getDistTo(centers[j]) < VertexVO.radius) return false
                }
            }
        }
        return true
    }

    private fun getIds(vertexes: ArrayList<Vertex>): ArrayList<Long> {
        val ids: ArrayList<Long> = arrayListOf()
        for (i in 0 until vertexes.size) {
            ids.add(vertexes[i].getId())
        }
        return ids
    }

    private fun getCenters(vertexes: ArrayList<Vertex>): ArrayList<Point> {
        val centers: ArrayList<Point> = arrayListOf()
        for (i in 0 until vertexes.size) {
            centers.add(vertexes[i].getCenter())
        }
        return centers
    }
}
