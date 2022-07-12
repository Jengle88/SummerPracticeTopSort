package tests.algorithm

import data.`object`.Vertex
import data.`object`.Graph
import utils.algorithm.GraphAlgorithm

import org.jetbrains.skia.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GraphAlgorithmTest {

    // тестирование алгоритма

    @Test
    fun emptyTopSortTest() {
        val graph = Graph(arrayListOf())
        val expected: MutableMap<Vertex, Int> = mutableMapOf()
        assertEquals(expected, GraphAlgorithm.TopSort(graph))
    }

    @Test
    fun usualTopSortTest1() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf(1, 3))
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf(3))
        val vertex4 = Vertex(3, "D", Point(0.0f, 0.0f), 0, arrayListOf())
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4))

        val expected: MutableMap<Vertex, Int> = mutableMapOf(vertex1 to 0, vertex2 to 1, vertex3 to 2, vertex4 to 3)

        assertEquals(expected, GraphAlgorithm.TopSort(graph))
    }

    @Test
    fun usualTopSortTest2() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf(1))
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf())
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf(3))
        val vertex4 = Vertex(3, "D", Point(0.0f, 0.0f), 0, arrayListOf())
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4))

        val expected: MutableMap<Vertex, Int> = mutableMapOf(vertex1 to 2, vertex2 to 3, vertex3 to 0, vertex4 to 1)

        assertEquals(expected, GraphAlgorithm.TopSort(graph))
    }

    @Test
    fun cycleTopSortTest() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf(1))
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf(0))
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3))

        val expected: MutableMap<Vertex, Int> = mutableMapOf()
        assertEquals(expected, GraphAlgorithm.TopSort(graph))
    }

    // тестирование проверки на циклы

    @Test
    fun usualCycleTest1() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf(1))
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf(0))
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3))

        val expected = true
        assertEquals(expected, GraphAlgorithm.checkGraphForCycle(graph))
    }

    @Test
    fun usualCycleTest2() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf())
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf(1))
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3))

        val expected = true
        assertEquals(expected, GraphAlgorithm.checkGraphForCycle(graph))
    }

    @Test
    fun usualCycleTest3() {
        val vertex1 = Vertex(0, "A", Point(0.0f, 0.0f), 0, arrayListOf(1))
        val vertex2 = Vertex(1, "B", Point(0.0f, 0.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(0.0f, 0.0f), 0, arrayListOf())
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3))

        val expected = false
        assertEquals(expected, GraphAlgorithm.checkGraphForCycle(graph))
    }
}