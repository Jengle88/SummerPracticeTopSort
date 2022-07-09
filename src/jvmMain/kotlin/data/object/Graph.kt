package data.`object`

data class Graph(private var vertexes: ArrayList<Vertex>){

    fun addVertex(vertex: Vertex) { vertexes.add(vertex) }

    fun getVertex(name: String): Vertex? = vertexes.find { it.getName() == name }

    fun getVertex(id: Long): Vertex? = vertexes.find { it.getId() == id }

    fun getVertex(index: Int): Vertex? {
        if (index in vertexes.indices) return vertexes[index]
        return null
    }

    fun getVertexes() = vertexes

    fun linkVertexes(vertexId1: Long, vertexId2: Long) {
        getVertex(vertexId1)?.addEdge(vertexId2)
    }

    fun removeLink(vertexId1: Long, vertexId2: Long) {
        if (getVertex(vertexId1)?.removeEdge(vertexId2) != true)
            getVertex(vertexId2)?.removeEdge(vertexId1)
    }

    fun removeVertex(vertex: Vertex?): Boolean = vertexes.remove(vertex)

    fun removeVertexById(id: Long): Boolean = removeVertex(getVertex(id))

    fun setOrderToVertex(vertexId: Long, order: Int) {
        val vertex = getVertex(vertexId)
        if (vertex != null)
            vertex.order = order
    }

    fun clearGraph() { vertexes.clear() }

}