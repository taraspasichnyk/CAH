package com.eleks.cah.android.lobby

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.HintColor
import com.eleks.cah.android.theme.txtLight16
import com.eleks.cah.android.theme.txtMedium16
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.NavigationView
import com.eleks.cah.lobby.LobbyViewModel

@Preview
@Composable
private fun EnterNameScreenPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.background(Color.White)) {
            EnterName(LobbyViewModel())
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnterName(lobbyViewModel: LobbyViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isImeVisible =
            WindowInsets.imeAnimationTarget.asPaddingValues().calculateBottomPadding() != 0.dp

        val headerHeight = animateDpAsState(
            targetValue = if (isImeVisible) 120.dp else 180.dp
        )

        GameHeader(headerHeight = headerHeight.value)

        Spacer(Modifier.weight(1f))
        EnterNameView(lobbyViewModel)
        Spacer(Modifier.weight(1f))

        val state by lobbyViewModel.state.collectAsState()
        val focusManager = LocalFocusManager.current
        NavigationView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_36),
                    end = dimensionResource(R.dimen.padding_36),
                    bottom = dimensionResource(R.dimen.padding_44)
                ),
            actionButtonText = R.string.label_next,
            backButtonEnabled = !state.isLoading,
            actionButtonEnabled = state.isNextButtonEnabled,
            isActionButtonLoading = state.isLoading,
            onBackButtonClick = {
                focusManager.clearFocus()
                lobbyViewModel.onBackPressed()
            },
            onActionButtonClick = {
                focusManager.clearFocus()
                lobbyViewModel.onNextClicked()
            },
        )

        var columnHeightDp by remember { mutableStateOf(0.dp) }
        val keyboardHeight = WindowInsets.ime.asPaddingValues().calculateBottomPadding()
        Spacer(modifier = Modifier.height(0f.coerceAtLeast(keyboardHeight.value - columnHeightDp.value).dp))
        val density = LocalDensity.current
        CardBackground(R.drawable.bg_pattern_big, modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                columnHeightDp = density.run { it.height.toDp() }
            }) {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(44.dp)
            )
        }
    }
}

@Composable
private fun EnterNameView(lobbyViewModel: LobbyViewModel) {
    Text(
        text = stringResource(R.string.title_enter_name),
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
        val focusManager = LocalFocusManager.current
        val state by lobbyViewModel.state.collectAsState()
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.disabled),
                cursorColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.clearFocus()
                    if (state.isNextButtonEnabled) lobbyViewModel.onNextClicked()
                }
            ),
            value = state.name,
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.title_your_name),
                    style = txtMedium16,
                    color = HintColor
                )
            },

            onValueChange = { lobbyViewModel.validateName(it) },
            shape = inputShape,
            textStyle = txtMedium16,
            readOnly = state.isLoading
        )
    }
}