package ui.InformationTables.ActionTable

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.interactor.AlgorithmTableInteractor
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class ActionTableViewModel(
    private val actionTableInteractor: AlgorithmTableInteractor,
    private val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    private val actionTableState: MutableState<Map<String, String>>
) {
    fun fillTable() {
        CoroutineScope(Dispatchers.Main).launch {
            currentAlgorithm.collect { data ->
                when(data.first) {
                    Algorithm.ALG_TOP_SORT -> {
                        if (data.second == AlgorithmState.IN_PROGRESS_AUTO) {
                            actionTableInteractor.updateGraphTopSortInfo(actionTableState)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}