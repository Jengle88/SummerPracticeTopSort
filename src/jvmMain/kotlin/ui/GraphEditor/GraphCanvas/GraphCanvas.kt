package ui.GraphEditor.GraphCanvas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import data.repositoryImpl.MockGraphDataStorageInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.skia.Font



@Composable
@Preview
fun GraphCanvas(modifier: Modifier = Modifier) {






    /*
    val graphDataFlow = graphCanvasViewModel.getGraphData()
    val graphData = remember { mutableStateOf(ArrayList<VertexVO>()) }
    graphDataFlow.onEach { data ->
        graphData.value = data
    }

    Button(onClick = {
        graphCanvasViewModel.updateGraph()
        CoroutineScope(Dispatchers.Main).launch {
            graphDataFlow.collectLatest { data ->
                graphData.value = data
            }
        }


    }) {
        Text("Жмяк")
    }*/

    /*Canvas(
        modifier = modifier
    ) {

        /*
        val circle1x = 150f
        val circle1y = 150f
        val circle1radius = 50f
        val circle2x = circle1x + 400f
        val circle2y = circle1y
        val circle2radius = circle1radius

        // вершина 1
        val textLabel1 = "1234567"
        val font1 = Font().apply {
            size = 15f
        }
        val textOffset1 = getTextOffset(font1, textLabel1)
        drawContext.canvas.nativeCanvas.drawString(
            textLabel1,
            circle1x - textOffset1,
            circle1y + 5f,
            Font().apply {
                size = 15f
            },
            Paint().apply {
                color = Color.Black.toArgb()
            }
        )
        drawCircle(
            color = Color.Black,
            center = Offset(
                x = circle1x,
                y = circle1y
            ),
            radius = circle1radius,
            style = Stroke()
        )

        // вершина 2
        val textLabel2 = "12345678"
        val font2 = Font().apply {
            size = 15f
        }
        val textOffset2 = getTextOffset(font2, textLabel2)
        drawContext.canvas.nativeCanvas.drawString(
            textLabel2,
            circle2x - textOffset2,
            circle2y + 5f,
            Font().apply {
                size = 15f
            },
            Paint().apply {
                color = Color.Black.toArgb()
            }
        )
        drawCircle(
            color = Color.Black,
            radius = circle2radius,
            center = Offset(
                x = circle2x,
                y = circle2y
            ),
            style = Stroke()
        )

        // вершина 3
        val circle3x = circle1x + 200f
        val circle3y = circle1y + 200f
        val circle3radius = circle1radius
        val textLabel3 = "123456789"
        val font3 = Font().apply {
            size = 15f
        }
        val textOffset3 = getTextOffset(font3, textLabel3)
        drawContext.canvas.nativeCanvas.drawString(
            textLabel3,
            circle3x - textOffset3,
            circle3y + 5f,
            Font().apply {
                size = 15f
            },
            Paint().apply {
                color = Color.Black.toArgb()
            }
        )
        drawCircle(
            color = Color.Black,
            radius = circle3radius,
            center = Offset(
                x = circle3x,
                y = circle3y
            ),
            style = Stroke()
        )
        drawLine(
            color = Color.Black,
            start = Offset(
                x = circle1x + circle1radius,
                y = circle1y
            ),
            end = Offset(
                x = circle2x - circle2radius,
                y = circle2y
            ),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Black,
            start = Offset(
                x = circle1x + circle1radius,
                y = circle1y
            ),
            end = Offset(
                x = circle3x,
                y = circle3y - circle3radius
            ),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.Black,
            start = Offset(
                x = circle3x,
                y = circle3y - circle3radius
            ),
            end = Offset(
                x = circle2x - circle2radius,
                y = circle2y
            ),
            strokeWidth = 2f
        )*/
    }*/
}

