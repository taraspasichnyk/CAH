package com.eleks.cah.android.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.android.R
import com.eleks.cah.android.txtBold18
import com.eleks.cah.android.txtBold24

@Preview
@Composable
fun GameLabel(modifier: Modifier = Modifier, bigSize: Boolean = false) {
    Column(
        modifier = modifier.background(MaterialTheme.colors.primary).padding(
            top = dimensionResource(id = R.dimen.padding_medium),
            start = dimensionResource(id = if (bigSize) R.dimen.padding_60 else R.dimen.padding_48),
            end = dimensionResource(id = if (bigSize) R.dimen.padding_60 else R.dimen.padding_48),
            bottom = dimensionResource(id = R.dimen.padding_small)
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.card_label),
                color = MaterialTheme.colors.secondary,
                style = if (bigSize) txtBold24 else txtBold18
            )
            Image(
                painter = painterResource(id = if (bigSize) R.drawable.ic_new_big else R.drawable.ic_new),
                contentDescription = "",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
            )
        }
        Text(
            text = stringResource(R.string.conflict_label),
            color = MaterialTheme.colors.secondary,
            style = if (bigSize) txtBold24 else txtBold18
        )
    }
}