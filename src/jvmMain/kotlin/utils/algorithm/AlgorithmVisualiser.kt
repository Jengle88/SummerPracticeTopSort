package utils.algorithm

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.skiko.currentNanoTime
import ui.GraphEditor.GraphCanvas.VertexVO

object AlgorithmVisualiser {
    private var algorithmProtocolPosition: Int = -1
    var algorithmProtocol: AlgorithmProtocol? = null
    set(value) {
        field = value
        algorithmProtocolPosition = 0
    }
    val graphCanvasData: MutableStateFlow<Map<Long, (VertexVO) -> Unit>> = MutableStateFlow(mapOf())
    val resultTableData: MutableStateFlow<MutableList<Pair<String, String>>> = MutableStateFlow(mutableListOf())
    val actionsTableData: MutableStateFlow<MutableList<Pair<String, String>>> = MutableStateFlow(mutableListOf())
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
        if (algorithmProtocolPosition + 1 < algorithmProtocol!!.getCountActions())
            algorithmProtocolPosition++
    }

    fun stepBack() { // TODO: 11.07.2022 Проверить
        if(checkProtocolSize()) return
        if (algorithmProtocolPosition - 1 > algorithmProtocol!!.getCountActions())
            algorithmProtocolPosition--
    }

    fun finishVisualise() {
        if (checkProtocolSize()) return
        algorithmProtocolPosition = (algorithmProtocol?.getCountActions() ?: 0) - 1
    }

    private fun checkProtocolSize(): Boolean {
        if ((algorithmProtocol?.getCountActions() ?: -1) <= 0)
            return true
        return false
    }
}
