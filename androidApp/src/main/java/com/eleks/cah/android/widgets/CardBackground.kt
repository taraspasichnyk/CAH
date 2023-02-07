package com.eleks.cah.android.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.eleks.cah.android.R

class PatternProvider : PreviewParameterProvider<Int> {
    override val values =
        listOf(R.drawable.bg_pattern_big, R.drawable.bg_pattern_normal).asSequence()
}

@Preview
@Composable
fun CardBackground(
    @PreviewParameter(PatternProvider::class)
    @DrawableRes patternId: Int
) {
    val image = ImageBitmap.imageResource(patternId)
    val brush = remember(image) {
        ShaderBrush(ImageShader(image, TileMode.Repeated, TileMode.Repeated))
    }
    Box(Modifier.background(brush).fillMaxSize()) {}
}