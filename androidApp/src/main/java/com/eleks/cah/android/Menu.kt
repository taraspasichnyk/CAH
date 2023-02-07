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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.menu.MenuContract
import com.eleks.cah.menu.MenuViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun Menu(
    menuViewModel: MenuViewModel = koinViewModel(),
    onNavigationRequired: (Route) -> Unit = {},
    onExit: () -> Unit = {}
) {

    LaunchedEffect(key1 = MenuViewModel.NAVIGATION_EFFECTS_KEY) {
        menuViewModel.effect.collectLatest {
            when (it) {
                is MenuContract.Effect.Navigation.NewGameScreen -> {
                   onNavigationRequired(Route.NewGame)
                }
                is MenuContract.Effect.Navigation.JoinGameScreen -> {
                    onNavigationRequired(Route.JoinGame)
                }
                is MenuContract.Effect.Navigation.SettingsScreen -> {
                    onNavigationRequired(Route.Settings)
                }
                is MenuContract.Effect.Navigation.Exit -> {
                    onExit()
                }
            }
        }
    }

    Surface(
        Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary
    ) {

        CardBackground(R.drawable.bg_pattern_big)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.padding_100),
                    bottom = dimensionResource(id = R.dimen.padding_100)
                ), contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_30),
                            start = dimensionResource(id = R.dimen.padding_60),
                            end = dimensionResource(id = R.dimen.padding_60),
                            bottom = dimensionResource(id = R.dimen.padding_10)
                        )
                ) {
                    Row() {
                        Text(
                            text = stringResource(R.string.card_label),
                            color = MaterialTheme.colors.secondary,
                            style = labelLarge
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_new),
                            contentDescription = "",
                            modifier = Modifier
                                .size(
                                    width = dimensionResource(id = R.dimen.padding_48),
                                    height = dimensionResource(id = R.dimen.padding_20)
                                )
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = stringResource(R.string.conflict_label),
                        color = MaterialTheme.colors.secondary,
                        style = labelLarge
                    )
                }
            }

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
                Button(
                    shape = RectangleShape,
                    onClick = { menuViewModel.onNewGameSelected() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_game_label),
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_big),
                            vertical = dimensionResource(R.dimen.padding_small)
                        ),
                        style = labelMedium
                    )
                }

                Button(
                    shape = RectangleShape,
                    onClick = { menuViewModel.onJoinGameSelected() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_big))
                ) {
                    Text(
                        text = stringResource(R.string.connect_to_game_label),
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_big),
                            vertical = dimensionResource(R.dimen.padding_small)
                        ),
                        style = labelMedium
                    )
                }

                Button(
                    shape = RectangleShape,
                    onClick = { menuViewModel.onSettingsSelected() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_big))

                ) {
                    Text(
                        text = stringResource(R.string.settings_label),
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_big),
                            vertical = dimensionResource(R.dimen.padding_small)
                        ),
                        style = labelMedium
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.padding_100)),
            contentAlignment = Alignment.BottomCenter
        ) {
            OutlinedButton(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                onClick = { menuViewModel.onExitSelected() },
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MenuPreview() {
    MyApplicationTheme {
        Menu()
    }
}
