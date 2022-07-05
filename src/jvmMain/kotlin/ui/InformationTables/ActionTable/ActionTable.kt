package ui.InformationTables.ActionTable

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
fun ActionTable(modifier: Modifier = Modifier) {
    val lazyColumnState = rememberLazyListState(
        initialFirstVisibleItemIndex = 0
    )
    val timeCardWeight = 40f
    val actionCardWeight = 100f

    Column(
        modifier = modifier
    ) {
        Row(Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(timeCardWeight),
                text = "Время",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier
                    .weight(actionCardWeight),
                text = "Действия",
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
                            "time: $i ms",
                            "result: ${i+5} action",
                            timeCardWeight,
                            actionCardWeight
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
