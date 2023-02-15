package com.eleks.cah.android.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.eleks.cah.android.R

val unboundedFontFamily = FontFamily(
    Font(R.font.unbounded_light, FontWeight.Light),
    Font(R.font.unbounded_regular, FontWeight.Normal),
    Font(R.font.unbounded_medium, FontWeight.Medium),
    Font(R.font.unbounded_bold, FontWeight.Bold),
    Font(R.font.unbounded_semi_bold, FontWeight.SemiBold)
)

val labelLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    lineHeight = 32.sp,
)

val labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 32.sp,
)

val labelMedium = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 16.sp,
)

private fun getDefaultTextStyle(
    fontSize: TextUnit? = null,
    fontWeight: FontWeight? = null,
): TextStyle {
    return TextStyle(
        fontFamily = unboundedFontFamily,
        fontWeight = fontWeight,
        fontSize = fontSize ?: 16.sp
    )
}

val txtBold18 = getDefaultTextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
)

val txtBold16 = getDefaultTextStyle(
    fontWeight = FontWeight.Bold
)

val txtBold24 = getDefaultTextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
)

val txtMedium12 = getDefaultTextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp
)

val txtMedium14 = getDefaultTextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp
)

val txtMedium16 = getDefaultTextStyle(
    fontWeight = FontWeight.Medium,
)

val txtSemibold24 = getDefaultTextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
)

val txtSemibold32 = getDefaultTextStyle(
    fontWeight = FontWeight.SemiBold,
    fontSize = 32.sp,
)

val txtSemibold16 = getDefaultTextStyle(
    fontWeight = FontWeight.SemiBold
)

val txtLight14 = getDefaultTextStyle(
    fontWeight = FontWeight.Light,
    fontSize = 14.sp
)

val txtLight16 = getDefaultTextStyle(
    fontWeight = FontWeight.Light,
)

val txtRegular18 = getDefaultTextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
)

val txtRegular11 = getDefaultTextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
)