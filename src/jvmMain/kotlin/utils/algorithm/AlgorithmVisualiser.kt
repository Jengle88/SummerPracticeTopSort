package utils.algorithm

import actions.State.State
import androidx.compose.ui.graphics.Color
import data.`object`.Vertex
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skiko.currentNanoTime
import ui.GraphEditor.GraphCanvas.VertexVO
import java.util.ArrayList

object AlgorithmVisualiser {
    private var algorithmProtocolPosition: Int = -1
    var algorithmProtocol: AlgorithmProtocol? = null
    set(value) {
        field = value
        algorithmProtocolPosition = 0
    }
    val graphCanvasData: MutableStateFlow<Map<Long, (VertexVO) -> VertexVO>> = MutableStateFlow(mapOf())
    var listOfCanvasVisualise: Map<Int, Map<Long, (VertexVO) -> VertexVO>> = mapOf()
    val resultTableData: MutableStateFlow<List<Pair<Vertex, Int>>> = MutableStateFlow(listOf())
    val actionsTableData: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )
    var algorithmState: AlgorithmState = AlgorithmState.NONE
    var visualiseJob: Job? = null

    fun startVisualiseTopSortAlgorithm(periodMs: Long) {
        visualiseJob = CoroutineScope(Dispatchers.Default).launch {
            var currentTimeMs = currentNanoTime() / 1000
            while (algorithmProtocolPosition < (algorithmProtocol?.getCountActions() ?: 0)
            ) {
                if (isActive) {
                    while (currentNanoTime() / 1000 - currentTimeMs < periodMs) {}
                    // TODO: 11.07.2022 Передача данных

                    currentTimeMs = currentNanoTime() / 1000
                }
            }
        }
    }

    fun pauseVisualise() {
        visualiseJob?.cancel()
    }

    fun stepNext() { // TODO: 11.07.2022 Проверить
        if (checkProtocolSize()) return
        algorithmState = AlgorithmState.IN_PROGRESS_USER
        if (algorithmProtocolPosition + 1 < algorithmProtocol!!.getCountActions())
            algorithmProtocolPosition++
        updateAlgorithmVisualiserState()
    }

    fun stepBack() { // TODO: 11.07.2022 Проверить
        if(checkProtocolSize()) return
        algorithmState = AlgorithmState.IN_PROGRESS_USER
        if (algorithmProtocolPosition - 1 >= 0)
            algorithmProtocolPosition--
        updateAlgorithmVisualiserState()
    }

    fun finishVisualise() {
        if (checkProtocolSize()) return
        algorithmState = AlgorithmState.FINISH
        algorithmProtocolPosition = (algorithmProtocol?.getCountActions() ?: 0) - 1
        updateAlgorithmVisualiserState()
    }

    private fun checkProtocolSize(): Boolean {
        if ((algorithmProtocol?.getCountActions() ?: -1) <= 0)
            return true
        return false
    }

    fun loadResult(
        topSortResult: Map<Vertex, Int>,
        protocol: ArrayList<State>
    ) {
        algorithmProtocol = AlgorithmProtocol(
            listOfActions = protocol.mapIndexed { index, state -> Pair("$index) time: ${state.time}", state.action) }
        )
        resultTableData.value = topSortResult.map { result -> Pair(result.key, result.value) }
        var index = 0
        listOfCanvasVisualise = protocol.associate { state -> index++ to mapOf(
            state.srcVertex to { vertex -> vertex.copy(color = Color.Yellow) },
            (state.dstVertex ?: -1) to { vertex -> vertex.copy(color = Color.Red) }
        )
        }
        algorithmState = AlgorithmState.IN_PROGRESS_AUTO
        updateAlgorithmVisualiserState()
    }

    private fun updateAlgorithmVisualiserState() {
        actionsTableData.value = algorithmProtocol?.getListActions(0 until algorithmProtocolPosition) ?: listOf()
        graphCanvasData.value = listOfCanvasVisualise[algorithmProtocolPosition - 1] ?: mapOf()
    }
}
