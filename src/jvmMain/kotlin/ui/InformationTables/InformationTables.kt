package ui.InformationTables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import ui.InformationTables.ActionTable.ActionTable
import ui.InformationTables.AlgorithmTable.AlgorithmTable
import utils.algorithm.Algorithm
import utils.GraphToolsState
import utils.algorithm.AlgorithmState

@Composable
@Preview
fun InformationTables(
    graphToolsStateFlow: MutableStateFlow<GraphToolsState>,
    currentAlgorithm: MutableStateFlow<Pair<Algorithm, AlgorithmState>>,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
    ) {
        AlgorithmTable(
            currentAlgorithm = currentAlgorithm,
            modifier = Modifier
                .weight(45f)
        )
        Spacer(Modifier
            .width(16.dp)
        )
        ActionTable(
            currentAlgorithm = currentAlgorithm,
            modifier = Modifier
                .weight(100f)
        )
    }
}


