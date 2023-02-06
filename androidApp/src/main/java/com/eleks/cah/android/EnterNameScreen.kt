package com.eleks.cah.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabel

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
            NavigationView()
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

@Composable
private fun NavigationView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(
            start = dimensionResource(R.dimen.padding_36),
            end = dimensionResource(R.dimen.padding_36),
            bottom = dimensionResource(R.dimen.padding_44)
        )
    ) {
        IconButton(
            onClick = {
                //TODO
            },
        ) {
            Icon(
                tint = Color.Unspecified,
                painter = painterResource(id = R.drawable.ic_back), contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        BlackButton(text = R.string.label_next, onClick = {
            //TODO
        })
    }
}
