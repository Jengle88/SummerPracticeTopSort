package algorithm

import data.dataGraph.*

object GraphAlgorithm {

    private fun initEdges(graph: Graph, current: Vertex): MutableMap<Int, Vertex> {
        val vertexes = graph.getVertexes()
        val edges: MutableMap<Int, Vertex> = mutableMapOf()
        for (vertex in vertexes) {
            if (vertex.getId() in current.getEdges()) edges[vertex.getId()] = vertex
        }
        return edges
    }

    private fun TopSortUtil(graph: Graph, current: Vertex, visited: ArrayList<Vertex>, order: Int):
            MutableMap <Vertex, Int> {
        visited.add(current)
        val edges: MutableMap<Int, Vertex> = initEdges(graph, current)
        val result: MutableMap<Vertex, Int> = mutableMapOf()
        for (edge in edges.values) {
            if (edge !in visited) {
                result += this.TopSortUtil(graph, edge, visited, order + 1)
            }
        }
        result[current] = order
        return result
    }

    fun TopSort(graph: Graph): Map<Vertex, Int> {
        var result: MutableMap<Vertex, Int> = mutableMapOf()
        val vertexes = graph.getVertexes()
        val visited: ArrayList<Vertex> = arrayListOf()
        for (vertex in vertexes) {
            if (vertex !in visited) {
                result = TopSortUtil(graph, vertex, visited, 0)
            }
        }
        return result
    }
}