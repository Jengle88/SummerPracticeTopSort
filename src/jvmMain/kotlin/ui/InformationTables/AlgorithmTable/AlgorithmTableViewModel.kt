package ui.InformationTables.AlgorithmTable

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import models.interactor.AlgorithmTableInteractor
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class AlgorithmTableViewModel(
    private val algorithmTableInteractor: AlgorithmTableInteractor,
    private val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    private val algorithmTableState: MutableState<Map<String, String>>
) {
    fun fillTable() {
        CoroutineScope(Dispatchers.Main).launch {
            currentAlgorithm.collect { data ->
                when(data.first) {
                    Algorithm.ALG_TOP_SORT -> {
                        if (data.second == AlgorithmState.IN_PROGRESS_AUTO) { // Или лучше AlgorithmState.FINISH?
                            algorithmTableInteractor.updateGraphTopSortInfo(algorithmTableState)
//                            currentAlgorithm.value = Pair(Algorithm.NONE, AlgorithmState.NONE)
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}