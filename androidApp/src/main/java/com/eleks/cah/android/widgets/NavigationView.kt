package com.eleks.cah.android.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.eleks.cah.android.R

@Composable
fun NavigationView(
    modifier: Modifier = Modifier,
    actionButtonEnabled: Boolean = true,
    @StringRes actionButtonText: Int,
    onBackButtonClick: () -> Unit,
    onActionButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            modifier = Modifier.bounceClick(),
            onClick = {
                onBackButtonClick()
            },
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "")
        }
        Spacer(modifier = Modifier.weight(1f))
        BlackButton(text = actionButtonText, enabled = actionButtonEnabled, onClick = {
            onActionButtonClick()
        })
    }
}
