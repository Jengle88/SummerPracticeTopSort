package ui.UserActionHint

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
@Preview
fun UserActionHint(modifier: Modifier = Modifier) {
    Text(
        text = "Пояснение: Выберите точке, где будет располагаться вершина",
        modifier = modifier,
        style = TextStyle(
            color = Color.Gray
        ),
        textAlign = TextAlign.Center

    )

}