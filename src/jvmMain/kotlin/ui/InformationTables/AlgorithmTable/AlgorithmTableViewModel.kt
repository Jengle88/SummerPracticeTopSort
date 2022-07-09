package ui.InformationTables.AlgorithmTable

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
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
            currentAlgorithm.collectLatest { data ->
                if (data.first == Algorithm.ALG_TOP_SORT && data.second == AlgorithmState.FINISH) {
                    algorithmTableState.value =
                        algorithmTableInteractor.getGraphTopSortInfo()
                    currentAlgorithm.value = Pair(Algorithm.NONE, AlgorithmState.NONE)
                }
            }
        }

    }
}