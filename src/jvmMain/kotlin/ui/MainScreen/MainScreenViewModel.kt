package ui.MainScreen

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import data.graphData.DataGraphLocator
import utils.GraphToolsState
import kotlinx.coroutines.flow.MutableStateFlow
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class MainScreenViewModel {
    val graphToolsState: MutableStateFlow<GraphToolsState> = MutableStateFlow(GraphToolsState.WAITING)
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
                val focusRequester = remember { FocusRequester() }
                TextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    value = nameFile.value,
                    label = { Text(alertDialogLabel) },
                    onValueChange = { name ->
                        nameFile.value = name
                    }
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
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