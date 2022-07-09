package utils.algorithm

import data.`object`.Graph
import data.`object`.Vertex
import java.util.*
import kotlin.collections.ArrayList

object GraphAlgorithm {

    private fun initEdges(graph: Graph, current: Vertex): MutableMap<Long, Vertex> {
        val vertexes = graph.getVertexes()
        val edges: MutableMap<Long, Vertex> = mutableMapOf()
        for (vertex in vertexes) {
            if (vertex.getId() in current.getEdges())
                edges[vertex.getId()] = vertex
        }
        return edges
    }

    private fun TopSortUtil(graph: Graph, current: Vertex, visited: ArrayList<Vertex>, stackOfVertexes: Stack<Vertex>) {
        visited.add(current)
        val edges: MutableMap<Long, Vertex> = initEdges(graph, current)
        for (edge in edges.values) {
            if (edge !in visited) {
                this.TopSortUtil(graph, edge, visited, stackOfVertexes)
            }
        }
        stackOfVertexes.add(current)
    }

    fun TopSort(graph: Graph): Map<Vertex, Int> {
        val stackOfVertexes: Stack<Vertex> = Stack()
        val vertexes = graph.getVertexes()
        val visited: ArrayList<Vertex> = arrayListOf()
        for (vertex in vertexes) {
            if (vertex !in visited) {
                TopSortUtil(graph, vertex, visited, stackOfVertexes)
            }
        }
        var order = 0
        val result: MutableMap<Vertex, Int> = mutableMapOf()
        while (stackOfVertexes.isNotEmpty()) {
            result[stackOfVertexes.pop()] = order++
        }
        return result
    }
}

