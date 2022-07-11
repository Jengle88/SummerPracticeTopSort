package models.interactor

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import utils.algorithm.AlgorithmVisualiser

class AlgorithmTableInteractorImpl: AlgorithmTableInteractor {
    override fun updateGraphTopSortInfo(table: MutableState<Map<String, String>>) {
        CoroutineScope(Dispatchers.Main).launch {
            AlgorithmVisualiser.resultTableData.collect { data ->
                table.value = data.associate { (vertex, order) ->
                    Pair(
                        "${vertex.getName()} (id: ${vertex.getId()})",
                        "Order: $order"
                    )
                }
            }
        }
    }
}