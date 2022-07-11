package models.interactor

import androidx.compose.runtime.MutableState

interface AlgorithmTableInteractor {
    fun updateGraphTopSortInfo(table: MutableState<Map<String, String>>)
}