package ui.InformationTables.AlgorithmTable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.InformationTables.TableItem

@Composable
@Preview
fun AlgorithmTable(modifier: Modifier = Modifier) {
    val lazyColumnState = rememberLazyListState(
        initialFirstVisibleItemIndex = 0
    )
    val numberCardWeight = 40f
    val resultCardWeight = 100f

    Column(
        modifier = modifier
    ) {
        Row(Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(numberCardWeight),
                text = "Вершина",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .weight(resultCardWeight),
                text = "Результат",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )

        }

        Box {
            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .padding(end = 12.dp)
            ) {
                for (i in 1..30) {
                    item {
                        TableItem(
                            "n: $i",
                            "result: ${i+5}",
                            numberCardWeight,
                            resultCardWeight
                        )
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = lazyColumnState
                )

            )
        }

    }

}
