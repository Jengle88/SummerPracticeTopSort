// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import ui.GraphEditor.GraphEditor
import ui.InformationTables.InformationTables
import ui.MainScreen.MainScreenViewModel
import ui.TopBarElement.MainScreenTopBar
import ui.UserActionHint.UserActionHint

@Composable
@Preview
fun MainScreen() {
    MaterialTheme{
        Scaffold(
            topBar = {
                MainScreenTopBar()
            }
        ) {
            MainContent()
        }
    }
}
@Composable
fun MainContent() {
    val mainScreenViewModel = remember { mutableStateOf(MainScreenViewModel()) }
    Column {
        UserActionHint(
            editorStateFlow = mainScreenViewModel.value.editorState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        GraphEditor(
            editorStateFlow = mainScreenViewModel.value.editorState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
        )
        InformationTables(Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp, minWidth = 150.dp)
            .padding(8.dp)
        )
    }
}


fun main() = singleWindowApplication(
    title = "Top Sort",
    state = WindowState(
        size = DpSize(1200.dp, 1000.dp),
        isMinimized = true
    )
) {
    MainScreen()
}
