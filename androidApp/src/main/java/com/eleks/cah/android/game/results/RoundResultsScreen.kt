package com.eleks.cah.android.game.results

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R.drawable
import com.eleks.cah.android.R.string
import com.eleks.cah.android.mockedGameRoom
import com.eleks.cah.android.theme.defaultDimens
import com.eleks.cah.android.theme.txtMedium14
import com.eleks.cah.android.theme.txtSemibold16
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import com.eleks.cah.domain.model.GameRoom
import com.eleks.cah.domain.model.Player

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RoundResultsScreenPreview() {
    MyApplicationTheme {
        RoundResultsScreen(
            gameRoom = mockedGameRoom(),
            onNextPressed = {}
        )
    }
}

@Composable
fun RoundResultsScreen(
    gameRoom: GameRoom,
    onNextPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GameHeader(
            gameLabelSize = GameLabelSize.SMALL,
            headerHeight = AppTheme.dimens.headerSize
        )

        Spacer(
            modifier = Modifier.height(AppTheme.dimens.sizeMedium)
        )

        val previousRoundNumber = gameRoom.currentRound?.number?.let { it - 1 } ?: return
        Text(
            text = stringResource(id = string.round_results, previousRoundNumber),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = txtSemibold16,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(46.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LeaderboardView(
                players = gameRoom.players.sortedByDescending { it.score }
            )

            BlackButton(
                onClick = onNextPressed,
                text = string.label_next,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = defaultDimens.sizeMedium)
            )
        }

        CardBackground(
            drawable.bg_pattern_big,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = defaultDimens.sizeMedium)
        ) {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(44.dp)
            )
        }
    }
}

@Composable
private fun ColumnScope.LeaderboardView(players: List<Player>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        itemsIndexed(players) { index, player ->
            PlayerItemView(player, index)
        }
    }
}

@Composable
private fun PlayerItemView(player: Player, position: Int) {
    Row(
        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${position + 1}. ${player.nickname}",
                style = txtMedium14,
                color = MaterialTheme.colors.primary,
                maxLines = 2,
                modifier = Modifier.weight(1f, fill = false)
            )

            if (position == 0) {
                Icon(
                    painter = painterResource(id = drawable.ic_game_owner),
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Text(
            text = "${player.score}",
            style = txtMedium14,
            color = MaterialTheme.colors.primary,
            maxLines = 1
        )
    }
    Divider(color = MaterialTheme.colors.primary)
}