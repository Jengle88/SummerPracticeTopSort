package topSort

import data.*

object GraphAlgorithm {

    fun DFS(graph: Graph, current: Vertex, visited: Array<Boolean>, order: Int, edges: MutableMap<Int, Vertex?>) {
        val vertexes = graph.getVertexes()
        val dict: MutableMap<Int, Vertex?> = mutableMapOf()
        for (vertex in vertexes) {
            dict[vertex.getId()] = vertex
        }

        visited[current.getId()] = true
        for (vertex in edges.keys){
            val edge = edges[vertex]
            if (!visited[edge!!.getId()]) {
                val temp_dict: MutableMap<Int, Vertex?> = mutableMapOf()
                for (key in dict.keys){
                    if (key in current.getEdges()){
                        val temp = dict[key]
                        temp_dict[key] = temp
                    }
                }
                val nextOrder = order + 1
                this.DFS(graph, edge, visited, nextOrder, temp_dict)
            }
        }
        current.setOrder(order)
    }

    fun TopSort(graph:Graph) {
        val vertexes = graph.getVertexes()
        val dict: MutableMap<Int, Vertex?> = mutableMapOf()
        for (vertex in vertexes) {
            dict[vertex.getId()] = vertex
        }
        val visited: Array<Boolean> = Array(vertexes.size, {false})
        val order = 0
        for (vertex in vertexes) {
            if (!visited[vertex.getId()]){
                val temp_dict: MutableMap<Int, Vertex?> = mutableMapOf()
                for (key in dict.keys){
                    if (key in vertex.getEdges()){
                        val temp = dict[key]
                        temp_dict[key] = temp
                    }
                }
                this.DFS(graph, vertex, visited, order, temp_dict)
            }
        }
    }

}
