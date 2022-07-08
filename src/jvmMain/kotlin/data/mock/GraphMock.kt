package data.mock

import org.jetbrains.skia.Point

val graph = arrayListOf(
    VertexMock(
        name = "1",
        order = 1,
        Point(25f, 25f),
        arrayListOf()
    ),
    VertexMock(
        name = "2",
        order = 2,
        Point(55f, 25f),
        arrayListOf()
    ),
    VertexMock(
        name = "3",
        order = 3,
        Point(25f, 55f),
        arrayListOf()
    ),
    VertexMock(
        name = "4",
        order = 4,
        Point(55f, 55f),
        arrayListOf()
    ),
)