package com.eleks.cah.android.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.txtRegular18

@Composable
fun BlackButton(modifier: Modifier = Modifier, onClick: () -> Unit, @StringRes text: Int) {
    Button(
        onClick = {
            onClick()
        },
        //TODO resources
        shape = RoundedCornerShape(2.dp),
        contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
        ),

        //TODO resources
        modifier = modifier.heightIn(56.dp)
    ) {
        Text(
            text = stringResource(text),
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(
                horizontal = dimensionResource(R.dimen.padding_48),
            ),
            style = txtRegular18,
        )
    }
}