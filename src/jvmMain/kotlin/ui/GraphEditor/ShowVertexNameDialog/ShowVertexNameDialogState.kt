package ui.GraphEditor.ShowVertexNameDialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowVertexNameDialogState(
    vertexNameForAlertDialog: MutableState<String>
): MutableState<Boolean> {
    val alertDialogState = remember { mutableStateOf(false) }
    if (alertDialogState.value && vertexNameForAlertDialog.value != "") {
        AlertDialog(
            modifier = Modifier
                .size(width = 300.dp, height = 100.dp),
            onDismissRequest = {
                alertDialogState.value = false
            },
            title = {
                Text("Полное название вершины")
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = vertexNameForAlertDialog.value
                    )
                }
            },
            buttons = {

            }
        )
    }
    return alertDialogState
}
