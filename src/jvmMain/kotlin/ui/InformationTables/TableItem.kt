package ui.InformationTables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TableItem(
    firstElem: String,
    secondElem: String,
    firstCardWeight: Float,
    secondCardWeight: Float
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(firstCardWeight)
                .height(30.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black
                ),
            text = firstElem
        )
        Text(
            modifier = Modifier
                .weight(secondCardWeight)
                .height(30.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black
                ),
            text = secondElem
        )
    }
}
