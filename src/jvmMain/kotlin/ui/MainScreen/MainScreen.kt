// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe
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
//
//    val a = MutableStateFlow(1)
//    val text = remember { mutableStateOf("") }
//    a.onEach { data ->
//        text.value = data.toString()
//    }.launchIn(CoroutineScope(Dispatchers.Main))
//
//    Button(
//        onClick = {
//            a.value++
//        }
//    ) {
//        Text(
//            text = text.value
//        )
//    }

    val mainScreenViewModel = MainScreenViewModel()
    Column {
        UserActionHint(
            editorStateFlow = mainScreenViewModel.editorState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        GraphEditor(
            editorStateFlow = mainScreenViewModel.editorState,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 450.dp, minWidth = (800+250).dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
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
