package data.`object`

import org.jetbrains.skia.Point

data class Vertex(
    private var id: Long,
    private var name: String,
    private val center: Point,
    var order: Int = -1,
    private var edges: ArrayList<Long> = ArrayList()
){
    fun getName() = name

    fun getId() = id

    fun getCenter(): Point = center

    fun addEdge(edge: Long) { edges.add(edge) }

    fun removeEdge(edge: Long): Boolean = edges.remove(edge)


    fun getEdges(): ArrayList<Long> = edges
}