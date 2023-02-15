package com.eleks.cah.android.widgets

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.theme.txtRegular18

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val contentAlpha by animateFloatAsState(targetValue = if (isLoading) 0f else 1f)
    val loadingAlpha by animateFloatAsState(targetValue = if (isLoading) 1f else 0f)
    Button(
        onClick = { if (!isLoading) onClick() },
        shape = RoundedCornerShape(2.dp),
        enabled = enabled,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
        modifier = modifier
            .heightIn(56.dp)
            .widthIn(170.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .graphicsLayer { alpha = loadingAlpha },
                color = MaterialTheme.colors.secondary,
                strokeWidth = 4.dp
            )
            Text(
                text = stringResource(text),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .graphicsLayer { alpha = contentAlpha }
                    .padding(horizontal = 16.dp),
                style = txtRegular18,
            )
        }
    }
}