package com.eleks.cah.android.lobby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.HintColor
import com.eleks.cah.android.theme.txtLight16
import com.eleks.cah.android.theme.txtMedium16
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.NavigationView
import com.eleks.cah.lobby.LobbyViewModel

@Preview
@Composable
private fun EnterNameScreenPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.background(Color.White)) {
            EnterCode(LobbyViewModel(true))
        }
    }
}


@Composable
fun EnterCode(lobbyViewModel: LobbyViewModel) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GameHeader()
        Column(
            modifier = Modifier.fillMaxSize().imePadding().navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            EnterCodeView(lobbyViewModel)
            Spacer(Modifier.weight(1f))
            NavigationView(
                modifier = Modifier.fillMaxWidth().padding(
                    start = dimensionResource(R.dimen.padding_36),
                    end = dimensionResource(R.dimen.padding_36),
                    bottom = dimensionResource(R.dimen.padding_44)
                ),
                actionButtonText = R.string.label_next,
                onBackButtonClick = {
                    lobbyViewModel.onBackPressed()
                },
                onActionButtonClick = {
                    lobbyViewModel.onNextClicked()
                },
            )
        }
    }
}

@Composable
private fun EnterCodeView(lobbyViewModel: LobbyViewModel) {
    Text(
        text = stringResource(R.string.title_enter_game_code),
        color = MaterialTheme.colors.primary,
        style = txtLight16,
    )
    val inputShape = RoundedCornerShape(4.dp)
    Card(
        elevation = 4.dp,
        shape = inputShape,
        modifier = Modifier.padding(
            start = dimensionResource(R.dimen.padding_big),
            top = dimensionResource(R.dimen.padding_medium),
            end = dimensionResource(R.dimen.padding_big)
        )
    ) {
        val textState by lobbyViewModel.state.collectAsState()
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.disabled),
                cursorColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.clearFocus()
                    lobbyViewModel.onNextClicked()
                }
            ),
            value = textState.code,
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.hint_game_code),
                    style = txtMedium16,
                    color = HintColor
                )
            },

            onValueChange = { lobbyViewModel.validateCode(it) },
            shape = inputShape,
            textStyle = txtMedium16
        )
    }
}
