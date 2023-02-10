package com.eleks.cah.android.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.round.cardPaddings
import com.eleks.cah.android.round.dropShadow
import com.eleks.cah.android.theme.*

@Composable
fun ConflictCard(cardText: String, modifier: Modifier = Modifier, isMasterCard: Boolean = false) {
    val gradientCard = if (isMasterCard) {
        remember { listOf(MineShaftDark, MineShaft) }
    } else {
        remember { listOf(Gallery, White50, GalleryGrey) }
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(Color.Transparent)
            .shadow(0.dp)
            .dropShadow(
                color = ShadowColor,
                borderRadius = AppTheme.dimens.sizeSmall,
                    blur = AppTheme.dimens.sizeMedium,
                offsetX = AppTheme.dimens.ZERO,
                offsetY = AppTheme.dimens.cardShadowYOffset,
                spread = AppTheme.dimens.ZERO
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .size(AppTheme.dimens.cardWidth, AppTheme.dimens.cardHeight)
                .clip(MaterialTheme.shapes.medium)
                .background(brush = Brush.linearGradient(gradientCard))
                .padding(cardPaddings()),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                cardText,
                color = if (isMasterCard) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}