package com.eleks.cah.android

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.theme.labelLarge
import com.eleks.cah.android.theme.labelMedium
import com.eleks.cah.android.widgets.BlackButton
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val windowInfo = rememberWindowInfo()


            BigSpace(windowInfo)

            HeaderView()

            BigSpace(windowInfo)

            ButtonsView(menuViewModel)
        }
    }
}

@Composable
private fun HeaderView() {
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
        Row {
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

@Composable
private fun ButtonsView(menuViewModel: MenuViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.secondary)
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        BlackButton(text = R.string.create_game_label, onClick = {
            menuViewModel.onNewGameSelected()
        })

        BlackButton(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_big)),
            text = R.string.connect_to_game_label,
            onClick = {
                menuViewModel.onJoinGameSelected()
            })

        SmallSpace()

        ExitView(menuViewModel)

        SmallSpace()
    }
}

@Composable
private fun SmallSpace() {
    val windowInfo = rememberWindowInfo()

    Spacer(
        modifier = Modifier.height(
            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                60.dp
            } else {
                120.dp
            }
        )
    )
}

@Composable
private fun BigSpace(windowInfo: WindowInfo) {

    when (windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compact -> Log.e("BigSpace ", "Compact")
        is WindowInfo.WindowType.Medium -> Log.e("BigSpace ", "Medium")
        else -> Log.e("BigSpace ", "Expand")
    }

    Spacer(
        modifier = Modifier.height(
            if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                60.dp
            } else {
                120.dp
            }
        )
    )
}

@Composable
private fun ExitView(menuViewModel: MenuViewModel) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        ExitButton(
            text = R.string.exit_label,
            onClick = { menuViewModel.onExitSelected() }
        )
    }
}

@Composable
fun ExitButton(modifier: Modifier = Modifier, onClick: () -> Unit, @StringRes text: Int) {
    OutlinedButton(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        onClick = { onClick() },
        border = BorderStroke(
            dimensionResource(id = R.dimen.small_border), MaterialTheme.colors.primary
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary
        )
    ) {
        Text(
            text = stringResource(text),
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_big),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
            style = labelMedium
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MenuPreview() {
    MyApplicationTheme {
        Menu()
    }
}
