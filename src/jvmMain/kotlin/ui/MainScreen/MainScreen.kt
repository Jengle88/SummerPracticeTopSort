// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import ui.GraphTools.GraphTools
import ui.InformationTables.InformationTables
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
    Column {
        UserActionHint(Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
        GraphTools(Modifier
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
