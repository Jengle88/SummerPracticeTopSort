package utils.algorithm

import data.`object`.Graph
import data.`object`.Vertex
import java.util.*
import kotlin.collections.ArrayList
import utils.actions.Actions
import utils.actions.State.State
import org.jetbrains.skiko.currentNanoTime
import org.jetbrains.skia.Point

object GraphAlgorithm {
    private var startTime = 0L
    private fun addState(
        srcVertex: Vertex, dstVertex: Vertex? = null, action: Actions,
        protocol: ArrayList<State>
    ) {
        when (action) {
            Actions.DOWN_TO_EDGE -> protocol.add(
                State(
                    getCurrentTime(),
                    "Down from vertex ${srcVertex.getName()} (id = ${srcVertex.getId()}) to ${dstVertex?.getName()} (id = ${dstVertex?.getId()})",
                    srcVertex.getId(),
                    dstVertex?.getId()
                )
            )

            Actions.GET_ORDER -> protocol.add(
                State(
                    getCurrentTime(),
                    "Vertex ${srcVertex.getName()} (id = ${srcVertex.getId()}) got order",
                    srcVertex.getId()
                )
            )

            Actions.ADDED_TO_STACK -> protocol.add(
                State(
                    getCurrentTime(),
                    "Vertex ${srcVertex.getName()} (id = ${srcVertex.getId()}) added to stack",
                    srcVertex.getId()
                )
            )
        }
    }

    private fun getCurrentTime(): String {
        return (currentNanoTime() / 1000000 - startTime).toString()
    }

    fun checkGraphForCycle(graph: Graph) : Boolean {
        val vertexes = graph.getVertexes()
        val visited: ArrayList<Vertex> = arrayListOf()
        val checkList: ArrayList<Boolean> = arrayListOf()
        for (vertex in vertexes) {
            if (vertex !in visited) {
                checkGraphForCycleUtil(graph, vertex, visited, checkList)
            }
        }
        return (false in checkList)
    }

    private fun checkGraphForCycleUtil(
        graph: Graph,
        current: Vertex,
        visited: ArrayList<Vertex>,
        checkList: ArrayList<Boolean>
    ) {
        visited.add(current)
        val edges = initEdges(graph, current)
        for (edge in edges.values) {
            if (edge !in visited) {
                checkGraphForCycleUtil(graph, edge, visited, checkList)
            } else {
                checkList.add(false)
            }
        }
        visited.remove(current)
        checkList.add(true)
    }

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
        if (checkGraphForCycle(graph)) return mapOf()
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


    private fun TopSortUtilActions(
        graph: Graph, current: Vertex, visited: ArrayList<Vertex>,
        stackOfVertexes: Stack<Vertex>, protocol: ArrayList<State>
    ) {
        visited.add(current)
        val edges: MutableMap<Long, Vertex> = initEdges(graph, current)
        for (edge in edges.values) {
            if (edge !in visited) {
                addState(current, edge, Actions.DOWN_TO_EDGE, protocol)
                this.TopSortUtilActions(graph, edge, visited, stackOfVertexes, protocol)
            }
        }
        stackOfVertexes.add(current)
        addState(current, action = Actions.ADDED_TO_STACK, protocol = protocol)
    }

    fun TopSortActions(graph: Graph): Pair<Map<Vertex, Int>, ArrayList<State>> {
        val stackOfVertexes: Stack<Vertex> = Stack()
        val protocol: ArrayList<State> = arrayListOf()
        val vertexes = graph.getVertexes()
        startTime = setStartAlgoTime()
        val visited: ArrayList<Vertex> = arrayListOf()
        if (checkGraphForCycle(graph)) return Pair(
            mapOf(),
            arrayListOf(State(getCurrentTime(), "Cycle was found, create another graph", 0))
        )
        for (vertex in vertexes) {
            if (vertex !in visited) {
                TopSortUtilActions(graph, vertex, visited, stackOfVertexes, protocol)
            }
        }
        var order = 0
        val result: MutableMap<Vertex, Int> = mutableMapOf()
        while (stackOfVertexes.isNotEmpty()) {
            val current = stackOfVertexes.pop()
            result[current] = order++
            addState(current, action = Actions.GET_ORDER, protocol = protocol)
        }
        return Pair(result, protocol)
    }

    private fun setStartAlgoTime() = currentNanoTime() / 1000000
}

