package com.eleks.cah.android.round

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.R.*
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RoundScreen(cards: List<Card>) {
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val itemWidth = AppTheme.dimens.cardWidth
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentPadding = PaddingValues(
        start = 120.dp, end = 120.dp
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            HorizontalPager(
                count = cards.size,
                state = state,
                contentPadding = contentPadding,
                itemSpacing = 0.dp,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) { page ->
                if (page == 0) {
                    UserCard(
                        cardText = stringResource(id = string.miy_instrument),
                        modifier = Modifier
                            .zIndex(1f)
                            .clickable {
                                scope.launch {
                                    state.animateScrollToPage(page)
                                }
                            }
                    )
                } else {
                    UserCard(
                        cardText = stringResource(id = string.miy_instrument),
                        modifier = Modifier
                            .zIndex(0f)
                            .rotate(page * 3f)
                            .clickable {
                                scope.launch {
                                    state.animateScrollToPage(page)
                                }
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun UserCard(cardText: String, modifier: Modifier = Modifier) {
    val gradientCard = remember { listOf(Gallery, White50, GalleryGrey) }

    Box(
        modifier = modifier
            .wrapContentSize()
            .background(Color.Transparent)
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
            Text(cardText, color = Color.Black)
        }
    }
}

@Composable
fun cardPaddings(): PaddingValues =
    PaddingValues(
        start = AppTheme.dimens.sizeSmall,
        end = AppTheme.dimens.sizeSmall,
        top = AppTheme.dimens.sizeMedium
    )

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard(cardText = stringResource(id = string.miy_instrument))
}