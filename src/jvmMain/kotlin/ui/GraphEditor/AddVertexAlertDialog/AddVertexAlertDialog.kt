package ui.GraphEditor.AddVertexAlertDialog

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ui.GraphEditor.AddVertexAlertDialog.AddVertexAlertDialogViewModel.ConfirmButton
import ui.GraphEditor.AddVertexAlertDialog.AddVertexAlertDialogViewModel.DismissButton
import ui.GraphEditor.AddVertexAlertDialog.AddVertexAlertDialogViewModel.dismissRequest
import ui.GraphEditor.AddVertexAlertDialog.AddVertexAlertDialogViewModel.TextFieldForName

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddVertexAlertDialog(
    vertexName: MutableStateFlow<String>
): MutableState<Boolean> {
    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        val name = remember { mutableStateOf("") }
        AlertDialog(
            modifier = Modifier
                .sizeIn(maxWidth = 325.dp, maxHeight = 250.dp),
            onDismissRequest = {
                dismissRequest(openDialog, vertexName)
            },
            confirmButton = {
                ConfirmButton(openDialog, vertexName, name)
            },
            dismissButton = {
                DismissButton(openDialog, vertexName)
            },
            title = {
                Text("Введите название вершины")
            },
            text = {
                TextFieldForName(name)
            }
        )
    }
    return openDialog
}

