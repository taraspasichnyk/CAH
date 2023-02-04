package com.eleks.cah.android.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val touchTarget: Dp,
    val cardWidth: Dp,
    val cardHeight: Dp,
    val sizeSmall: Dp = 8.dp,
    val sizeMedium: Dp = 16.dp,
    val sizeBig: Dp = 24.dp,
    val cardShadowYOffset: Dp = 12.dp,
    val ZERO: Dp = 0.dp
)

val defaultDimens = Dimens(
    touchTarget = 48.dp,
    cardWidth = 180.dp,
    cardHeight = 240.dp
)

val smallDimens = Dimens(
    touchTarget = 48.dp,
    cardWidth = 180.dp,
    cardHeight = 240.dp
)