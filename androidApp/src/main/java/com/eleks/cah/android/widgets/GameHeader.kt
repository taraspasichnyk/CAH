package com.eleks.cah.android.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.R

@Composable
fun GameHeader(
    gameLabelSize: GameLabelSize = GameLabelSize.MEDIUM,
    headerHeight: Dp = 180.dp
) {
    Layout(
        content = {
            CardBackground(
                patternId = R.drawable.bg_pattern_big,
                modifier = Modifier
                    .height(headerHeight)
                    .fillMaxWidth()
            )
            GameLabel(size = gameLabelSize)
        },
        measurePolicy = { measurables, constraints ->
            val backgroundPlaceable = measurables[0].measure(constraints)
            val gameLabelPlaceable = measurables[1].measure(constraints)

            val height = backgroundPlaceable.height + gameLabelPlaceable.height / 2
            val width = constraints.maxWidth
            layout(width, height) {
                backgroundPlaceable.placeRelative(0, 0)
                gameLabelPlaceable.placeRelative(
                    (width - gameLabelPlaceable.width) / 2,
                    height - gameLabelPlaceable.height
                )
            }
        }
    )
}

