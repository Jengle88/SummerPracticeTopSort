package data

data class Graph(private var vertexes: ArrayList<Vertex>){

    fun getVertexes() = vertexes

    fun getVertex(name: String): Vertex? = vertexes.find { it.getName() == name }

    fun getVertex(index: Int): Vertex? {
        if (index in vertexes.indices) return vertexes[index]
        return null
    }

    fun removeVertex(vertex: Vertex): Boolean = vertexes.remove(vertex)

    fun addVertex(vertex: Vertex) { vertexes.add(vertex) }

    fun clearVertexList() { vertexes.clear() }

}