package tests.parsing

import data.`object`.Vertex
import data.`object`.Graph
import utils.parsing.Parser

import org.jetbrains.skia.Point
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ParserTest {

    // тестирование считывания
    @Test
    fun emptyLoadTest() {
        val filePath = "./src/jvmMain/kotlin/tests/parsing/loadTests/emptyLoad.json"
        val expected: Graph = Graph(arrayListOf())
        assertEquals(expected, Parser.readDataJSON(filePath))
    }

    @Test
    fun errorLoadTest() {
        val filePath = "./src/jvmMain/kotlin/tests/parsing/loadTests/errorLoad.json"
        val expected: Graph = Graph(arrayListOf())
        assertEquals(expected, Parser.readDataJSON(filePath))
    }

    @Test
    fun usualLoadTest() {
        val filePath = "./src/jvmMain/kotlin/tests/parsing/loadTests/usualLoad.json"
        val vertex1 = Vertex(0, "A", Point(10.0f, 15.0f), 0, arrayListOf(1, 3))
        val vertex2 = Vertex(1, "B", Point(100.0f, 150.0f), 0, arrayListOf(2))
        val vertex3 = Vertex(2, "C", Point(50.0f, 15.0f), 0, arrayListOf(3))
        val vertex4 = Vertex(3, "D", Point(75.0f, 200.0f), 0, arrayListOf())
        val expected: Graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4))
        assertEquals(expected, Parser.readDataJSON(filePath))
    }

    // тестирование сохранения

    @Test
    fun usualSaveTest() {
        val vertex1 = Vertex(0, "A", Point(10.0f, 20.0f), 0, arrayListOf(1, 3, 4))
        val vertex2 = Vertex(1, "B", Point(100.0f, 200.0f), 0, arrayListOf(2, 3))
        val vertex3 = Vertex(2, "C", Point(150.0f, 250.0f), 0, arrayListOf(3, 4))
        val vertex4 = Vertex(3, "D", Point(10.0f, 120.0f), 0, arrayListOf(4))
        val vertex5 = Vertex(4, "E", Point(180.0f, 220.0f), 0, arrayListOf())
        Parser.writeDataJSON("./src/jvmMain/kotlin/tests/parsing/saveTests/usualSave.json", Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4, vertex5)))
    }

    @Test
    fun emptySaveTest() {
        Parser.writeDataJSON("./src/jvmMain/kotlin/tests/parsing/saveTests/emptySave.json", Graph(arrayListOf()))
    }

    @Test
    fun usualSaveTest2() {
        val vertex1 = Vertex(0, "A", Point(10.0f, 20.0f), 0, arrayListOf())
        val vertex2 = Vertex(1, "B", Point(100.0f, 200.0f), 0, arrayListOf())
        val vertex3 = Vertex(2, "C", Point(150.0f, 250.0f), 0, arrayListOf())
        val vertex4 = Vertex(3, "D", Point(10.0f, 120.0f), 0, arrayListOf())
        val vertex5 = Vertex(4, "E", Point(180.0f, 220.0f), 0, arrayListOf())
        Parser.writeDataJSON("./src/jvmMain/kotlin/tests/parsing/saveTests/usualSave.json", Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4, vertex5)))
    }

    // тестирование проверки данных на валидность

    @Test
    fun usualCheckTest() {
        val vertex1 = Vertex(0, "A", Point(10.0f, 20.0f), 0, arrayListOf(0))
        val vertex2 = Vertex(1, "B", Point(100.0f, 200.0f), 0, arrayListOf(1))
        val vertex3 = Vertex(2, "C", Point(150.0f, 250.0f), 0, arrayListOf(2))
        val vertex4 = Vertex(3, "D", Point(10.0f, 120.0f), 0, arrayListOf(3))
        val vertex5 = Vertex(4, "E", Point(180.0f, 220.0f), 0, arrayListOf())
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4, vertex5))

        val expected = true
        assertEquals(expected, Parser.checkGraphForValid(graph))
    }

    @Test
    fun wrongIdCheckTest() {
        val vertex1 = Vertex(0, "A", Point(10.0f, 20.0f), 0, arrayListOf(0))
        val vertex2 = Vertex(1, "B", Point(100.0f, 200.0f), 0, arrayListOf(1))
        val vertex3 = Vertex(1, "C", Point(150.0f, 250.0f), 0, arrayListOf(2))
        val vertex4 = Vertex(3, "D", Point(10.0f, 120.0f), 0, arrayListOf(3))
        val vertex5 = Vertex(4, "E", Point(180.0f, 220.0f), 0, arrayListOf())
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4, vertex5))

        val expected = false
        assertEquals(expected, Parser.checkGraphForValid(graph))
    }

    @Test
    fun wrongEdgesCheckTest() {
        val vertex1 = Vertex(0, "A", Point(10.0f, 20.0f), 0, arrayListOf(0, 2))
        val vertex2 = Vertex(1, "B", Point(100.0f, 200.0f), 0, arrayListOf(1))
        val vertex3 = Vertex(1, "C", Point(150.0f, 250.0f), 0, arrayListOf(2))
        val vertex4 = Vertex(3, "D", Point(10.0f, 120.0f), 0, arrayListOf(3, 4))
        val vertex5 = Vertex(4, "E", Point(180.0f, 220.0f), 0, arrayListOf(123321))
        val graph = Graph(arrayListOf(vertex1, vertex2, vertex3, vertex4, vertex5))

        val expected = false
        assertEquals(expected, Parser.checkGraphForValid(graph))
    }
}