package algorithm

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

    private fun TopSortUtil(graph: Graph, current: Vertex, visited: ArrayList<Vertex>, stackOfVertex: Stack<Vertex>) {
        visited.add(current)
        val edges: MutableMap<Long, Vertex> = initEdges(graph, current)
        for (edge in edges.values) {
            if (edge !in visited) {
                this.TopSortUtil(graph, edge, visited, stackOfVertex)
            }
        }
        stackOfVertex.add(current)
    }

    fun TopSort(graph: Graph): Map<Vertex, Int> {
        val stackOfVertex: Stack<Vertex> = Stack()
        val vertexes = graph.getVertexes()
        val visited: ArrayList<Vertex> = arrayListOf()
        for (vertex in vertexes) {
            if (vertex !in visited) {
                TopSortUtil(graph, vertex, visited, stackOfVertex)
            }
        }
        var order = 0
        val result: MutableMap<Vertex, Int> = mutableMapOf()
        while (stackOfVertex.isNotEmpty()) {
            result[stackOfVertex.pop()] = order++
        }
        return result
    }
}

