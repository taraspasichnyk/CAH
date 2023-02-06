package com.eleks.cah.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameLabel

@Composable
fun Menu(
    onNewGame: () -> Unit = {},
    onJoinGame: () -> Unit = {},
    onSettings: () -> Unit = {},
    onExit: () -> Unit
) {
    Surface(
        Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary
    ) {

        CardBackground(R.drawable.bg_pattern_big, modifier = Modifier.fillMaxSize())

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GameLabel(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_100),
                    bottom = dimensionResource(id = R.dimen.padding_100)
                ),
                bigSize = true,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.secondary)
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_60),
                        bottom = dimensionResource(
                            id = R.dimen.padding_210
                        )
                    )
            ) {
                BlackButton(text = R.string.create_game_label, onClick = {
                    onNewGame()
                })

                BlackButton(
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_big)
                    ),
                    text = R.string.connect_to_game_label,
                    onClick = {
                        onJoinGame()
                    })

                BlackButton(
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_big)
                    ),
                    text = R.string.settings_label,
                    onClick = {
                        onSettings()
                    })
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.padding_100)),
            contentAlignment = Alignment.BottomCenter
        ) {
            OutlinedButton(
                onClick = { onExit() },
                border = BorderStroke(
                    dimensionResource(id = R.dimen.small_border), MaterialTheme.colors.primary
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(
                    text = stringResource(R.string.exit_label),
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.padding_big),
                        vertical = dimensionResource(R.dimen.padding_small)
                    ),
                    style = labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun MenuPreview() {
    MyApplicationTheme {
        Menu {}
    }
}
