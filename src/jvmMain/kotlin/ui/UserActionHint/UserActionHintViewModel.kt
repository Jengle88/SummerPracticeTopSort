package ui.UserActionHint

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import utils.GraphToolsState

class UserActionHintViewModel {
    private fun getTitle(graphToolsState: GraphToolsState): String =
        when (graphToolsState) {
            GraphToolsState.WAITING -> "Пояснение: режим ожидания"
            GraphToolsState.SET_VERTEX -> "Пояснение: добавление вершины"
            GraphToolsState.REMOVE_VERTEX -> "Пояснение: удаление вершины"
            GraphToolsState.SET_EDGE_FIRST -> "Пояснение: выбор вершины, откуда будет выходить ребро"
            GraphToolsState.SET_EDGE_SECOND -> "Пояснение: выбор вершины, куда будет входить ребро"
            GraphToolsState.REMOVE_EDGE_FIRST -> "Пояснение: удаление ребра, выбор первой вершины"
            GraphToolsState.REMOVE_EDGE_SECOND -> "Пояснение: удаление ребра, выбор второй вершины"
            GraphToolsState.TO_BEGIN -> "Пояснение: перемотка в начало алгоритма"
            GraphToolsState.TO_FINISH -> "Пояснение: перемотка в конец алгоритма"
            GraphToolsState.CONTINUE -> "Пояснение: визуализация продолжается"
            GraphToolsState.PAUSE -> "Пояснение: визуализация приостановлена"
            else -> ""
        }
    fun subscribeTitleToEditorState(title: MutableState<String>, graphToolsStateFlow: MutableStateFlow<GraphToolsState>) {
        CoroutineScope(Dispatchers.Main).launch {
            graphToolsStateFlow.collect { state ->
                title.value = getTitle(state)
            }
        }

    }
}