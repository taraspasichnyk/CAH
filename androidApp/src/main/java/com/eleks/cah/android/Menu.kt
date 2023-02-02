package com.eleks.cah.android

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Menu(
    onNewGame: () -> Unit = {},
    onJoinGame: () -> Unit = {},
    onSettings: () -> Unit = {},
    onExit: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(top = 100.dp, bottom = 100.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .padding(top = 30.dp, start = 60.dp, end = 60.dp, bottom = 10.dp)
                ) {
                    Row() {
                        Text(text = stringResource(R.string.card_label), color = Color.White)
                        Image(
                            painter = painterResource(id = R.drawable.ic_new),
                            contentDescription = "new",
                            modifier = Modifier.size(width = 48.dp, height = 20.dp)
                        )
                    }
                    Text(text = stringResource(R.string.conflict_label), color = Color.White)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 60.dp, bottom = 210.dp)
            ) {
                Button(
                    shape = RectangleShape,
                    onClick = { onNewGame() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_game_label),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                Button(
                    shape = RectangleShape,
                    onClick = { onJoinGame() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                    ),
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.connect_to_game_label),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }

                Button(
                    shape = RectangleShape,
                    onClick = { onSettings() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                    ),
                    modifier = Modifier.padding(top = 24.dp)

                ) {
                    Text(
                        text = stringResource(R.string.settings_label),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 140.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            OutlinedButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                onClick = { onExit() },
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = stringResource(R.string.exit_label),
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }
}