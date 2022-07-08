package ui.GraphEditor.AddVertexAlertDialog

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow

object AddVertexAlertDialogViewModel {

    @Composable
    fun TextFieldForName(name: MutableState<String>) {
        TextField(
            value = name.value,
            onValueChange = {
                if (it.length <= 25)
                    name.value = it
            },
            singleLine = true,
            label = { Text("Название вершины") }
        )
    }

    @Composable
    fun DismissButton(openDialog: MutableState<Boolean>) {
        Button(
            onClick = {
                openDialog.value = false
            }
        ) {
            Text("Отменить")
        }
    }

    @Composable
    fun ConfirmButton(
        openDialog: MutableState<Boolean>,
        vertexName: MutableStateFlow<String>,
        name: MutableState<String>
    ) {
        Button(
            onClick = {
                openDialog.value = false
                vertexName.value = name.value
            }
        ) {
            Text("Сохранить")
        }
    }
}