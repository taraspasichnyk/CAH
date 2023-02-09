package com.eleks.cah.android.round

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.R.*
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.theme.*
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RoundScreen(cards: List<Card>, roundNumber: Int) {
    val state = rememberPagerState()
    val scope = rememberCoroutineScope()
    val itemWidth = AppTheme.dimens.cardWidth
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val contentPadding = PaddingValues(
        start = 100.dp, end = 100.dp
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            GameHeader(
                gameLabelSize = GameLabelSize.SMALL,
                headerHeight = AppTheme.dimens.headerSize
            )
            Spacer(
                modifier = Modifier.height(AppTheme.dimens.sizeMedium)
            )
            Text(
                text = stringResource(id = string.round, roundNumber),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = txtSemibold16,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier.height(AppTheme.dimens.sizeMedium)
            )
            ConflictCard(
                cardText = stringResource(id = string.master_card_placeholder),
                isMasterCard = true,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(
                modifier = Modifier
                    .defaultMinSize(
                        minHeight = AppTheme.dimens.sizeMedium
                    )
            )
            Text(
                text = stringResource(id = string.choose_answer),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = txtRegular11,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(AppTheme.dimens.sizeMedium)
            )
            Box {
                HorizontalPager(
                    count = cards.size,
                    state = state,
                    contentPadding = contentPadding,
                    itemSpacing = 0.dp,
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) { page ->
                    if (page != 0) {
                        ConflictCard(
                            cardText = stringResource(id = string.miy_instrument),
                            modifier = Modifier
                                .rotate(page * 20f)
                                .clickable {
                                    scope.launch {
                                        state.animateScrollToPage(page)
                                    }
                                }
                        )
                    }
                }

                ConflictCard(
                    cardText = stringResource(id = string.miy_instrument),
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                state.animateScrollToPage(0)
                            }
                        }
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.dimens.headerSize))
        }

        Button(
            shape = RectangleShape,
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
            ),
            modifier = Modifier
                .padding(bottom = AppTheme.dimens.sizeMedium)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(string.choose),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(
                    horizontal = AppTheme.dimens.sizeBig,
                    vertical = AppTheme.dimens.sizeSmall
                ),
                style = labelMedium
            )
        }
    }
}

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

@Composable
fun cardPaddings(): PaddingValues =
    PaddingValues(
        start = AppTheme.dimens.sizeSmall,
        end = AppTheme.dimens.sizeSmall,
        top = AppTheme.dimens.sizeMedium,
        bottom = AppTheme.dimens.sizeSmall
    )

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    ConflictCard(cardText = stringResource(id = string.miy_instrument))
}