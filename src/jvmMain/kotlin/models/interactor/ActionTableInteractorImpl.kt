package models.interactor

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.algorithm.AlgorithmVisualiser

class ActionTableInteractorImpl: AlgorithmTableInteractor {
    override fun updateGraphTopSortInfo(table: MutableState<Map<String, String>>) {
        CoroutineScope(Dispatchers.Main).launch {
            AlgorithmVisualiser.actionsTableData.collect { data ->
                table.value = data.associate { (time, action) ->
                    Pair(
                        time,
                        action
                    )
                }
            }
        }
    }

}