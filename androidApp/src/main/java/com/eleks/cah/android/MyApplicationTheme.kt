package com.eleks.cah.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eleks.cah.android.theme.Dimens
import com.eleks.cah.android.theme.defaultDimens
import com.eleks.cah.android.theme.smallDimens
import com.eleks.cah.android.theme.unboundedFontFamily

private val LocalDimens = staticCompositionLocalOf { defaultDimens }
@Composable
fun ProvideDimens(
    dimensions: Dimens,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalDimens provides dimensionSet, content = content)
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current

    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xff222222),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFFFFFFFF)
        )
    } else {
        lightColors(
            primary = Color(0xff222222),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFFFFFFFF)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = unboundedFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(dimensionResource(id = R.dimen.round_corner_small)),
        medium = RoundedCornerShape(dimensionResource(id = R.dimen.round_corner_medium)),
        large = RoundedCornerShape(dimensionResource(id = R.dimen.round_corner_big))
    )

    val dimensions = if (configuration.screenWidthDp <= 360) defaultDimens else smallDimens

    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

object AppTheme {
    val dimens: Dimens
        @Composable
        get() = LocalDimens.current
}