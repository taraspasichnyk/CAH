package com.eleks.cah.android.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.txtBold16
import com.eleks.cah.android.theme.txtBold18
import com.eleks.cah.android.theme.txtBold24

@Preview
@Composable
fun GameLabel(modifier: Modifier = Modifier, size: GameLabelSize = GameLabelSize.MEDIUM) {
    val cardPaddings: PaddingValues
    val cardTextStyle: TextStyle

    when (size) {
        GameLabelSize.SMALL -> {
            cardPaddings = PaddingValues(
                top = dimensionResource(id = R.dimen.padding_medium),
                start = dimensionResource(id = R.dimen.padding_big_extra),
                end = dimensionResource(id = R.dimen.padding_big_extra),
                bottom = dimensionResource(id = R.dimen.padding_small_extra)
            )
            cardTextStyle = txtBold16
        }
        GameLabelSize.MEDIUM -> {
            cardPaddings = PaddingValues(
                top = dimensionResource(id = R.dimen.padding_medium),
                start = dimensionResource(id = R.dimen.padding_48),
                end = dimensionResource(id = R.dimen.padding_48),
                bottom = dimensionResource(id = R.dimen.padding_small)
            )
            cardTextStyle = txtBold18
        }
        GameLabelSize.BIG -> {
            cardPaddings = PaddingValues(
                top = dimensionResource(id = R.dimen.padding_medium),
                start = dimensionResource(id = R.dimen.padding_60),
                end = dimensionResource(id = R.dimen.padding_60),
                bottom = dimensionResource(id = R.dimen.padding_small)
            )
            cardTextStyle = txtBold24
        }
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(cardPaddings)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.card_label),
                color = MaterialTheme.colors.secondary,
                style = cardTextStyle
            )
            Image(
                painter = painterResource(
                    id = if (size == GameLabelSize.BIG) R.drawable.ic_new_big else R.drawable.ic_new
                ),
                contentDescription = "",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }
        Text(
            text = stringResource(R.string.conflict_label),
            color = MaterialTheme.colors.secondary,
            style = cardTextStyle
        )
    }
}

enum class GameLabelSize {
    SMALL, MEDIUM, BIG
}