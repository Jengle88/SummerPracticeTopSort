package data

data class Vertex(
    private var name: String,
    private var id: Int,
    private var order: Int = 0,
    private var edges: ArrayList<Int>,
    private var xCoord: Float,
    private var yCoord: Float){

    fun getName(): String { return name }

    fun getId(): Int { return id }

    fun setOrder(order: Int) { this.order = order }

    fun removeEdge(edge: Int): Boolean { return edges.remove(edge) }

    fun addEdge(edge: Int) { edges.add(edge) }

    fun getCenter(): FloatArray { return floatArrayOf(xCoord, yCoord) }

    fun getEdges(): ArrayList<Int> { return edges }


}

data class Graph(private var vertexes: ArrayList<Vertex>){

    fun getVertexes(): ArrayList<Vertex> {
        return vertexes
    }

    fun getVertex(name: String): Vertex? { return vertexes.find { it.getName() == name } }

    fun getVertex(index: Int): Vertex? {
        if (index in vertexes.indices) return vertexes[index]
        return null
    }

    fun removeVertex(vertex: Vertex): Boolean { return vertexes.remove(vertex) }

    fun addVertex(vertex: Vertex) { vertexes.add(vertex) }

    fun clearVertexList() { vertexes.clear() }

}