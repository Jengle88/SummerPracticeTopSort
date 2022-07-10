package ui.TopBarElement

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MainScreenTopBar(
    onClickLoadGraphButton: () -> Unit,
    onClickSaveGraphButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                onClickLoadGraphButton()
            }) {
                Text("Загрузить")
            }
            Button(onClick = {
                onClickSaveGraphButton()
            }) {
                Text("Сохранить")
            }
        }
    }
}