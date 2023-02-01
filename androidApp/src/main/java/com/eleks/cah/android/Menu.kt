package com.eleks.cah.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.eleks.cah.GameViewModel

@Composable
fun Menu(
    onNewGame: () -> Unit = {},
    onJoinGame: () -> Unit = {},
    onSettings: () -> Unit = {},
    onExit: () -> Unit
) {
    Column {
        Text("New Game", Modifier.clickable { onNewGame() })
        Text("Join Game", Modifier.clickable { onJoinGame() })
        Text("Settings", Modifier.clickable { onSettings() })
        Text("Exit", Modifier.clickable { onExit() })
    }
}