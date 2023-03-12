package com.eleks.cah.android.game.round

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.txtSemibold32
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize

@Composable
fun PreRoundScreen(roundNumber: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        GameHeader(gameLabelSize = GameLabelSize.SMALL, headerHeight = AppTheme.dimens.headerSize)

        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.round, roundNumber),
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = txtSemibold32
        )
        Spacer(modifier = Modifier.weight(1f))

        CardBackground(R.drawable.bg_pattern_big, modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(44.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreRoundScreenPreview() {
    MyApplicationTheme {
        Box(modifier = Modifier.background(Color.White)) {
            PreRoundScreen(1)
        }
    }
}