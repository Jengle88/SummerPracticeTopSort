package ui.MainScreen

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.graphData.DataGraphLocator
import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class MainScreenViewModel {
    val editorState: MutableStateFlow<EditorState> = MutableStateFlow(EditorState.WAITING)
    val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>> = MutableStateFlow(
        Pair(Algorithm.NONE, AlgorithmState.NONE))

    // EnterFileNameAlertDialog
    private var alertDialogTitle: String = ""
    private var alertDialogLabel: String = ""
    private var confirmButtonAction: @Composable (String) -> Unit = {}
    private var dismissButtonAction: @Composable () -> Unit = {}
    private var onDismissAction: () -> Unit = {}

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun EnterFileNameAlertDialogFactory(
        alertDialogTitle: String = this.alertDialogTitle,
        alertDialogLabel: String = this.alertDialogLabel,
        confirmButtonAction: @Composable (String) -> Unit = this.confirmButtonAction,
        dismissButtonAction: @Composable () -> Unit = this.dismissButtonAction,
        onDismissAction: () -> Unit = this.onDismissAction,
    ) {
        val nameFile = remember { mutableStateOf("") }
        AlertDialog(
            title = { Text(alertDialogTitle) },
            onDismissRequest = {
                onDismissAction()
            },
            confirmButton = {
                confirmButtonAction(nameFile.value)
            },
            dismissButton = {
                dismissButtonAction()
            },
            text = {
                TextField(value = nameFile.value, label = { Text(alertDialogLabel) }, onValueChange = { name ->
                    nameFile.value = name
                })
            }
        )
    }

    fun prepareAlertDialogForLoadData(enterFileNameState: MutableState<Boolean>) {
        alertDialogTitle = "Введите имя файла для загрузки"
        alertDialogLabel = "Имя файла (без расширения)"
        confirmButtonAction = { nameFile ->
            Button(
                onClick = {
                    DataGraphLocator.readGraphData("$nameFile.json")
                    enterFileNameState.value = false
                }
            ) {
                Text("Загрузить")
            }
        }
        dismissButtonAction = {
            Button(
                onClick = {
                    enterFileNameState.value = false
                }
            ) {
                Text("Отмена")
            }
        }
        onDismissAction = {
            enterFileNameState.value = false
        }
    }

    fun prepareAlertDialogForSaveData(enterFileNameState: MutableState<Boolean>) {
        alertDialogTitle = "Введите имя файла для сохранения"
        alertDialogLabel = "Имя файла (без расширения)"
        confirmButtonAction = { nameFile ->
            Button(
                onClick = {
                    DataGraphLocator.saveGraphData("$nameFile.json")
                    enterFileNameState.value = false
                }
            ) {
                Text("Сохранить")
            }
        }
        dismissButtonAction = {
            Button(
                onClick = {
                    enterFileNameState.value = false
                }
            ) {
                Text("Отмена")
            }
        }
        onDismissAction = {
            enterFileNameState.value = false
        }
    }

    fun showEnterFileDialog(enterFileNameState: MutableState<Boolean>) {
        enterFileNameState.value = true
    }

}