package ui.GraphTools.GraphEditor

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun GraphEditor(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        val buttonModifier = Modifier
            .weight(0.5f)
            .align(Alignment.CenterHorizontally)
        val rowButtonsModifier = Modifier
            .fillMaxWidth()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            text = "Добавить элементы",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = rowButtonsModifier
        ) {
            IconButton(
                modifier = buttonModifier
                    .size(64.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource("drawable/add_vertex.ico"),
                    contentDescription = null
                )
            }
            IconButton(
                modifier = buttonModifier
                    .size(64.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource("drawable/remove_vertex.ico"),
                    contentDescription = null
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = rowButtonsModifier
        ) {
            IconButton(
                modifier = buttonModifier
                    .size(64.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource("drawable/add_edge.ico"),
                    contentDescription = null
                )
            }
            IconButton(
                modifier = buttonModifier
                    .size(64.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource("drawable/remove_edge.ico"),
                    contentDescription = null
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
            ,
            text = "Алгоритмы",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Row (
            modifier = rowButtonsModifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Button(
                modifier = buttonModifier,
                onClick = {}
            ) {
                Text("<- Шаг")
            }
            Button(
                modifier = buttonModifier,
                onClick = {}
            ) {
                Text("Шаг ->")
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {}
        ) {
            Text(
                text = "Топологическая сортировка",
                textAlign = TextAlign.Center
            )
        }
    }
}