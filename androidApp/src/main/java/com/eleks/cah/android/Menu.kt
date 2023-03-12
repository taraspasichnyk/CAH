package com.eleks.cah.android

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.android.theme.labelMedium
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameLabel
import com.eleks.cah.android.widgets.GameLabelSize
import com.eleks.cah.menu.MenuContract
import com.eleks.cah.menu.MenuViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun Menu(
    menuViewModel: MenuViewModel = koinViewModel(),
    onNavigationRequired: (String) -> Unit = {},
    onExit: () -> Unit = {}
) {
    LaunchedEffect(key1 = Unit) {
        menuViewModel.effect.collectLatest {
            when (it) {
                is MenuContract.Effect.Navigation.NewGameScreen -> {
                    onNavigationRequired(MainRoute.Lobby.getPath(true))
                }
                is MenuContract.Effect.Navigation.JoinGameScreen -> {
                    onNavigationRequired(MainRoute.Lobby.getPath(false))
                }
                is MenuContract.Effect.Navigation.Exit -> {
                    onExit()
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.height(350.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
            CardBackground(R.drawable.bg_pattern_big, modifier = Modifier.fillMaxSize())
            GameLabel(modifier = Modifier.statusBarsPadding(), size = GameLabelSize.BIG)
        }

        Spacer(modifier = Modifier.weight(1f))

        BlackButton(text = R.string.create_game_label, onClick = {
            menuViewModel.onNewGameSelected()
        })

        BlackButton(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_big)),
            text = R.string.connect_to_game_label,
            onClick = {
                menuViewModel.onJoinGameSelected()
            })

        Spacer(modifier = Modifier.weight(2f))

        ExitButton(
            text = R.string.exit_label,
            onClick = { menuViewModel.onExitSelected() })

        Spacer(modifier = Modifier.weight(2f))

        CardBackground(R.drawable.bg_pattern_big, modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(44.dp)
            )
        }
    }
}

@Composable
fun ExitButton(onClick: () -> Unit, @StringRes text: Int) {
    OutlinedButton(
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
    Menu()
}
