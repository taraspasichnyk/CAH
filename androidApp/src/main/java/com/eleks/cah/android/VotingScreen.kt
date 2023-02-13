package com.eleks.cah.android

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.round.cardPaddings
import com.eleks.cah.android.round.dropShadow
import com.eleks.cah.android.theme.*
import com.eleks.cah.android.widgets.AnimatedValueVisibility
import com.eleks.cah.android.widgets.ConflictCard
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import kotlinx.coroutines.delay

@Preview
@Composable
private fun VotingScreenPreview() {
    MyApplicationTheme {
        VotingScreen(1)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VotingScreen(
    roundNumber: Int
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {

        GameHeader(
            gameLabelSize = GameLabelSize.SMALL,
            headerHeight = AppTheme.dimens.headerSize
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(top = 33.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val answers = remember {
                mutableStateListOf(
                    Card("Poor life choices"),
                    Card("An extremely horny Granny Smith"),
                    Card("Free Samples"),
                    Card("Diarrhea"),
                    Card("Falling in love"),
                )
            }
            val pagerState = rememberPagerState()

            Text(
                text = stringResource(
                    R.string.round_n_vote,
                    roundNumber
                ),
                style = txtSemibold16,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            VotingTabs(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
                cardsCount = answers.size,
                pagerState = pagerState,
            )

            Spacer(Modifier.weight(1f))

            VotingContent(
                pagerState = pagerState,
                question = Card(" _ ! This is my fetish"),
                answers = answers,
            )

            Spacer(Modifier.weight(1f))

            val currentPage = pagerState.currentPage

            VoteOptions(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp),
                selectedVote = answers[currentPage].vote,
            ) {
                answers[currentPage] =
                    answers[currentPage]
                        .copy(vote = it)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VotingTabs(
    cardsCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val stripWeight = 1f / (cardsCount + 1)
        items(cardsCount) { page ->
            Tab(stripWeight, page == pagerState.currentPage)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.Tab(
    stripWeight: Float,
    isSelected: Boolean
) {
    Box(
        Modifier.Companion
            .fillParentMaxWidth(stripWeight)
            .height(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) {
                    Color.Black
                } else {
                    Color.LightGray
                }
            )
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun VotingContent(
    pagerState: PagerState,
    question: Card,
    answers: List<Card>
) {

    Box(modifier = Modifier, contentAlignment = Alignment.TopCenter) {
        ConflictCard(
            isMasterCard = true,
            cardText = question.text,
        )

        AnswerCards(
            answers,
            pagerState,
            Modifier
                .padding(top = AppTheme.dimens.cardHeight * 0.75f)
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun AnswerCards(
    answers: List<Card>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        pageCount = answers.size,
        state = pagerState,
        modifier = modifier
    ) {
        val answer = answers[it]

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.wrapContentHeight()
        ) {

            val angle = if (it % 2 == 0) -15.0f else 15.0f

            ConflictCard(
                cardText = answer.text,
                modifier = Modifier.rotate(angle),
            )

            AnimatedValueVisibility(answer.vote) { vote ->

                val painterRes = when (vote) {
                    1 -> painterResource(id = R.drawable.scores_0)
                    2 -> painterResource(id = R.drawable.scores_1)
                    3 -> painterResource(id = R.drawable.scores_2)
                    4 -> painterResource(id = R.drawable.scores_3)
                    else -> null
                }
                painterRes?.let {
                    Image(
                        painterRes,
                        "",

                        modifier = modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
private fun VoteOptions(
    modifier: Modifier = Modifier,
    selectedVote: Int? = null,
    onSelectedVoteChange: (Int?) -> Unit = {}
) {
    val gradientCard = remember {
        val startColor = Color(0xFFF0F0F0)
        val middleColor = Color(0xFFFFFFFF)
        val endColor = Color(0xFFEBEBEB)
        listOf(startColor, middleColor, endColor)
    }

    Box(
        modifier = modifier
            .height(136.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(brush = Brush.linearGradient(gradientCard), alpha = 0.4f)
            .padding(cardPaddings())
            .dropShadow(gradientCard[0]),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(4.dp),
                style = txtMedium14,
                text = stringResource(R.string.vote_scores_title),
                color = Color.White
            )

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VoteButton(
                    R.drawable.scores_0,
                    selectedVote == 1,
                    onVote = { onSelectedVoteChange(1) },
                    onUnvote = { onSelectedVoteChange(null) }
                )
                VoteButton(
                    R.drawable.scores_1,
                    selectedVote == 2,
                    onVote = { onSelectedVoteChange(2) },
                    onUnvote = { onSelectedVoteChange(null) }
                )
                VoteButton(
                    R.drawable.scores_2,
                    selectedVote == 3,
                    onVote = { onSelectedVoteChange(3) },
                    onUnvote = { onSelectedVoteChange(null) }
                )
                VoteButton(
                    R.drawable.scores_3,
                    selectedVote == 4,
                    onVote = { onSelectedVoteChange(4) },
                    onUnvote = { onSelectedVoteChange(null) }
                )
            }
        }
    }
}

@Composable
private fun VoteButton(
    @DrawableRes res: Int,
    isVoted: Boolean = false,
    onVote: () -> Unit = {},
    onUnvote: () -> Unit = {},
) {
    val source = MutableInteractionSource()

    val alpha: Float by animateFloatAsState(if (isVoted) 0.5f else 1f)
    val scale: Float by animateFloatAsState(if (isVoted) 0.5f else 1f)

    Image(
        painterResource(id = res),
        "",
        modifier = Modifier
            .clickable(source, null) {
                if (!isVoted) {
                    onVote()
                } else {
                    onUnvote()
                }
            }
            .scale(scale)
            .alpha(alpha)
    )
}