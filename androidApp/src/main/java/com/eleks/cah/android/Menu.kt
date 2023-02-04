package com.eleks.cah.android

import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.menu.MenuViewModel

@Composable
fun Menu(
    onNewGame: () -> Unit = {},
    onJoinGame: () -> Unit = {},
    onSettings: () -> Unit = {},
    onExit: () -> Unit
) {
    Column {
        Button(onClick = onNewGame) {
            Text(stringResource(R.string.menu_new_game_button_title))
        }
        Button(onClick = onJoinGame) {
            Text(stringResource(R.string.join_game_menu_button_title))
        }

        Button(onClick = onSettings) {
            Text(stringResource(R.string.settings_menu_button_title))
        }

        Button(onClick = onExit) {
            Text(stringResource(R.string.exit_menu_button_title))
        }

    }
}

@Composable
@Preview
fun Menu_preview() {
    Menu({}, {}, {}, {})
}