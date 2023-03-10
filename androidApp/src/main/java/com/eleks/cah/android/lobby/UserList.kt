package com.eleks.cah.android.lobby

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.*
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.NavigationView
import com.eleks.cah.android.widgets.bounceClick
import com.eleks.cah.domain.model.Player
import com.eleks.cah.lobby.LobbyViewModel

@Preview
@Composable
private fun UserListScreenPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.background(Color.White)) {
            UserList(LobbyViewModel())
        }
    }
}

@Composable
fun UserList(lobbyViewModel: LobbyViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GameHeader()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 48.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state by lobbyViewModel.state.collectAsState()
            GameCodeView(
                code = state.code,
                onCopyClick = {
                    lobbyViewModel.onCodeCopyClicked()
                },
                onShareClick = {
                    lobbyViewModel.onCodeShareClicked()
                },
            )

            UserListView(state.users)

            NavigationView(
                modifier = Modifier.fillMaxWidth(),
                backButtonEnabled = !state.isLoading,
                actionButtonEnabled = state.isNextButtonEnabled,
                isActionButtonLoading = state.isLoading,
                actionButtonText = if (lobbyViewModel.gameOwner) {
                    R.string.title_start_game
                } else {
                    R.string.title_ready
                },
                onBackButtonClick = {
                    lobbyViewModel.onBackPressed()
                },
                onActionButtonClick = {
                    lobbyViewModel.onNextClicked()
                },
            )
        }

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
private fun GameCodeView(code: String, onCopyClick: () -> Unit, onShareClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(4.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.title_code_for_connection),
                style = txtLight14,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = code,
                style = txtBold24,
                color = MaterialTheme.colors.primary
            )
        }

        IconButton(
            modifier = Modifier.bounceClick(),
            onClick = {
                onCopyClick()
            },
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_copy), contentDescription = "")
        }

        IconButton(
            modifier = Modifier.bounceClick(),
            onClick = {
                onShareClick()
            },
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_share), contentDescription = "")
        }
    }
}


@Composable
private fun ColumnScope.UserListView(users: List<Player>) {
    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentPadding = PaddingValues(top = 16.dp)
    ) {
        itemsIndexed(users) { index, user ->
            UserItemView(user, index)
        }
    }
}

@Composable
private fun UserItemView(user: Player, position: Int) {
    Row(
        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, end = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${position + 1}. ${user.nickname}",
                style = txtMedium14,
                color = MaterialTheme.colors.primary,
                maxLines = 2,
                modifier = Modifier.weight(1f, fill = false)
            )

            if (user.isGameOwner) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_game_owner),
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        Text(
            modifier = Modifier
                .defaultMinSize(76.dp)
                .background(
                    color = if (user.state != Player.PlayerState.NOT_READY) Green else Yellow,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp),
            text = stringResource(
                if (user.state != Player.PlayerState.NOT_READY) R.string.title_ready
                else R.string.title_is_waiting
            ),
            style = txtMedium12,
            color = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center
        )
    }
    Divider(color = MaterialTheme.colors.primary)
}