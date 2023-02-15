package com.eleks.cah.android.round

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.R.*
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.pxToDp
import com.eleks.cah.android.theme.*
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RoundScreen(cards: List<Card>, roundNumber: Int) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val userCards = remember {
        cards.toMutableStateList()
    }
    var chosenCard by remember {
        mutableStateOf<Card?>(null)
    }
    var selectedCardPosition by remember {
        mutableStateOf(Offset.Zero)
    }

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

            Spacer(modifier = Modifier.height(AppTheme.dimens.sizeMedium))

            ConflictCard(
                cardText = stringResource(id = string.master_card_placeholder),
                isMasterCard = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .onGloballyPositioned {
                        selectedCardPosition = it
                            .positionInWindow()
                            .plus(
                                Offset(
                                    -it.size.width / 2f,
                                    it.size.height.toFloat() / 2
                                )
                            )
                    }
            )

            Spacer(
                modifier = Modifier.defaultMinSize(minHeight = AppTheme.dimens.sizeMedium)
            )

            Text(
                text = stringResource(id = string.choose_answer),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = txtRegular11,
                color = Color.Black
            )

            UserHand(
                cards = userCards,
                coroutineScope = scope,
                pagerState = pagerState,
                onCardChosen = {}
            )
        }

        ChooseButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = {
                val previousCard = chosenCard
                chosenCard = userCards[pagerState.currentPage]

                if (pagerState.pageCount > 1) {
                    userCards.removeAt(pagerState.currentPage)
                    previousCard?.let { userCards.add(pagerState.currentPage, previousCard) }
                }
            }
        )

        chosenCard?.let {
            if (selectedCardPosition != Offset.Zero) {
                ConflictCard(
                    cardText = it.text,
                    modifier = Modifier
                        .scale(0.75f, 0.75f)
                        .padding(
                            start = selectedCardPosition.x.pxToDp(),
                            top = selectedCardPosition.y.pxToDp()
                        )
                        .rotate(-20f)
                        .clickable {
                            userCards.add(pagerState.currentPage, it)
                            chosenCard = null
                        }
                )
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserHand(
    cards: List<Card>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onCardChosen: (Int) -> Unit
) {
    val switchCardButtonSize = AppTheme.dimens.actionButtonSize

    Box(modifier = Modifier.wrapContentSize()) {

        UserCards(cards = cards, pagerState = pagerState, onScroll = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
                onCardChosen.invoke(it)
            }
        })

        if (cards.size > 1) {
            Icon(
                painter = painterResource(id = drawable.ic_back),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        coroutineScope.launch {
                            val previousPage = (pagerState.currentPage - 1)
                                .coerceAtLeast(0)
                            pagerState.animateScrollToPage(previousPage)
                            onCardChosen.invoke(previousPage)
                        }
                    }
                    .padding(
                        horizontal = switchCardButtonSize,
                        vertical = switchCardButtonSize * 2
                    ),
                contentDescription = null
            )

            Icon(
                painter = painterResource(id = drawable.ic_back),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .rotate(180f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        coroutineScope.launch {
                            val nextPage = (pagerState.currentPage + 1)
                                .coerceAtMost(pagerState.pageCount - 1)
                            pagerState.animateScrollToPage(nextPage)
                            onCardChosen.invoke(nextPage)
                        }
                    }
                    .padding(
                        horizontal = switchCardButtonSize,
                        vertical = switchCardButtonSize * 2
                    ),
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserCards(
    cards: List<Card>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onScroll: (Int) -> Unit,
    cardHeight: Dp = AppTheme.dimens.cardHeight
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                translationY = (-cardHeight.value)
            }
    ) {
        val contentPadding = maxWidth / 4f
        val itemSpacing = -(AppTheme.dimens.cardWidth / 2)
        val buttonSize = AppTheme.dimens.actionButtonSize
        val paddingFromParent = buttonSize.value * 1.5f
        val transY = AppTheme.dimens.cardHeight.value.minus(paddingFromParent)

        HorizontalPager(
            count = cards.count(),
            contentPadding = PaddingValues(horizontal = contentPadding),
            modifier = Modifier.height(cardHeight * 2f),
            itemSpacing = itemSpacing,
            state = pagerState,
            elevationType = PageElevation.FOCUSED_ABOVE
        ) { page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page)
                        //val pageOffsetAbsolute = pageOffset.absoluteValue
                        //val percentFromCenter = 1.0f - (pageOffsetAbsolute / (5f / 2f))
                        //val itemScale = 0.5f + (percentFromCenter * 0.5f).coerceIn(0f, 1f)

                        scaleY = if (pagerState.currentPage == page) {
                            1f
                        } else {
                            0.75f
                        }
                        scaleX = if (pagerState.currentPage == page) {
                            1f
                        } else {
                            0.75f
                        }
                        rotationZ = if (pagerState.currentPage == page) {
                            0f
                        } else {
                            pageOffset.coerceIn(-1f, 1f) * -7f
                        }
                        translationY = if (pagerState.currentPage == page) {
                            -paddingFromParent
                        } else {
                            transY
                        }
                    }
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        enabled = true,
                    ) {
                        onScroll.invoke(page)
                    }) {
                ConflictCard(cardText = cards[page].text)
            }
        }
    }
}

@Composable
fun ChooseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        shape = RectangleShape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
        ),
        modifier = modifier
            .padding(bottom = AppTheme.dimens.sizeMedium)
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

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun HorizontalPager() {
    UserCards(cards = listOf(), pagerState = rememberPagerState(), onScroll = {})
}