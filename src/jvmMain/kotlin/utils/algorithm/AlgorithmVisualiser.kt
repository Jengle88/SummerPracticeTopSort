package utils.algorithm

import utils.actions.State.State
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
    private var listOfCanvasVisualise: Map<Int, Map<Long, (VertexVO) -> VertexVO>> = mapOf()
    val resultTableData: MutableStateFlow<List<Pair<Vertex, Int>>> = MutableStateFlow(listOf())
    val actionsTableData: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(
        listOf()
    )
    var algorithmState: AlgorithmState = AlgorithmState.NONE
    var visualiseJob: Job? = null
    const val defaultPeriod = 500L

    fun startVisualiseTopSortAlgorithm(periodMs: Long) {
        algorithmState = AlgorithmState.IN_PROGRESS_AUTO
        visualiseJob = autoVisualisationJob(periodMs)
    }

    private fun autoVisualisationJob(periodMs: Long) =
        CoroutineScope(Dispatchers.Default).launch {
            var currentTimeMs = currentNanoTime() / 1000000
            while (isActive && algorithmProtocolPosition + 1 < (algorithmProtocol?.getCountActions() ?: 0)) {
                while (isActive && currentNanoTime() / 1000000 - currentTimeMs < periodMs) { }
                stepNext()
                currentTimeMs = currentNanoTime() / 1000000
            }
        }

    fun pauseVisualise() {
        visualiseJob?.cancel()
    }

    fun stopVisualise() {
        pauseVisualise()
        algorithmProtocol = null
        algorithmProtocolPosition = -1
        graphCanvasData.value = mapOf()
        listOfCanvasVisualise = mapOf()
        resultTableData.value = listOf()
        actionsTableData.value = listOf()
        algorithmState = AlgorithmState.NONE
    }

    fun stepNext() {
        if (checkProtocolSize()) return
        if (algorithmProtocolPosition + 1 < algorithmProtocol!!.getCountActions())
            algorithmProtocolPosition++
        updateAlgorithmVisualiserState()
    }

    fun stepBack() {
        if(checkProtocolSize()) return
        if (algorithmProtocolPosition - 1 >= 0)
            algorithmProtocolPosition--
        updateAlgorithmVisualiserState()
    }

    fun toFinish() {
        if (checkProtocolSize()) return
        pauseVisualise()
        algorithmProtocolPosition = (algorithmProtocol?.getCountActions() ?: 0) - 1
        updateAlgorithmVisualiserState()
    }
    fun toBegin() {
        if (checkProtocolSize()) return
        algorithmProtocolPosition = 0
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
            listOfActions = protocol.mapIndexed { index, state -> Pair("${index+1}) time: ${state.time} ms", state.action) }
        )
        resultTableData.value = topSortResult.map { result -> Pair(result.key, result.value) }
        var index = 0
        listOfCanvasVisualise = protocol.associate { state ->
            index++ to mapOf(
                state.srcVertex to { vertex -> vertex.copy(color = if((state.dstVertex ?: -1) == -1L) Color.Green else Color.Yellow) },
                (state.dstVertex ?: -1) to { vertex -> vertex.copy(color = Color.Red) }
            )
        }
        algorithmState = AlgorithmState.IN_PROGRESS_AUTO
        updateAlgorithmVisualiserState()
    }

    private fun updateAlgorithmVisualiserState() {
        actionsTableData.value = algorithmProtocol?.getListActions(0..algorithmProtocolPosition) ?: listOf()
        graphCanvasData.value = listOfCanvasVisualise[algorithmProtocolPosition] ?: mapOf()
    }
}
