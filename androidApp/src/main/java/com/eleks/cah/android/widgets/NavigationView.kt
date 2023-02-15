package com.eleks.cah.android.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.eleks.cah.android.R

@Composable
fun NavigationView(
    modifier: Modifier = Modifier,
    backButtonEnabled: Boolean = true,
    actionButtonEnabled: Boolean = true,
    isActionButtonLoading: Boolean = false,
    @StringRes actionButtonText: Int,
    onBackButtonClick: () -> Unit,
    onActionButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            modifier = Modifier.bounceClick(backButtonEnabled),
            enabled = backButtonEnabled,
            onClick = {
                onBackButtonClick()
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                tint = Color.Unspecified,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        LoadingButton(
            text = actionButtonText,
            enabled = actionButtonEnabled,
            isLoading = isActionButtonLoading,
            onClick = {
                onActionButtonClick()
            })
    }
}
