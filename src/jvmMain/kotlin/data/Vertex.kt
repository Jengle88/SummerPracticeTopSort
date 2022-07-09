package data

import org.jetbrains.skia.Point

data class Vertex(
    private var id: Int,
    private var name: String,
    private val center: Point,
    var order: Int = 0,
    private var edges: ArrayList<Int> = ArrayList()
){
    fun getName() = name

    fun getId(): Int = id

    fun getCenter(): Point = center

    fun addEdge(edge: Int) { edges.add(edge) }

    fun removeEdge(edge: Int): Boolean = edges.remove(edge)


    fun getEdges(): ArrayList<Int> = edges
}