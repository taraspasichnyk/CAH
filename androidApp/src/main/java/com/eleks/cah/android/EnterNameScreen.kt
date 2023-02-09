package com.eleks.cah.android

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.theme.txtLight16
import com.eleks.cah.android.theme.txtMedium16
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.NavigationView

@Preview
@Composable
private fun EnterNameScreenPreview() {
    MyApplicationTheme {
        EnterNameScreen()
    }
}

@Composable
fun EnterNameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        GameHeader()

        Column(
            modifier = Modifier.fillMaxSize().navigationBarsPadding().imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))
            EnterNameView()
            Spacer(Modifier.weight(1f))
            NavigationView(
                modifier = Modifier.fillMaxWidth().padding(
                    start = dimensionResource(R.dimen.padding_36),
                    end = dimensionResource(R.dimen.padding_36),
                    bottom = dimensionResource(R.dimen.padding_44)
                ),
                actionButtonText = R.string.label_next,
                onBackButtonClick = {
                    //TODO
                },
                onActionButtonClick = {
                    //TODO
                },
            )
        }
    }
}

@Composable
private fun EnterNameView() {
    var textState by remember { mutableStateOf(TextFieldValue()) }
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
        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.disabled),
                cursorColor = MaterialTheme.colors.primary),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.clearFocus()
                }
            ),
            value = textState,
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.title_your_name),
                    style = txtMedium16,
                    //TODO
                    color = Color(0xffAEAEAE)
                )
            },

            onValueChange = { textState = it },
            shape = inputShape,
            textStyle = txtMedium16
        )
    }
}