package ui.MainScreen

import utils.EditorState
import kotlinx.coroutines.flow.MutableStateFlow
import utils.algorithm.Algorithm
import utils.algorithm.AlgorithmState

class MainScreenViewModel {
    val editorState: MutableStateFlow<EditorState> = MutableStateFlow(EditorState.WAITING)
    val currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>> = MutableStateFlow(
        Pair(Algorithm.NONE, AlgorithmState.NONE))
}