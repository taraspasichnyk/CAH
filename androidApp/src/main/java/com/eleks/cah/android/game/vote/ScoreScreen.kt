package com.eleks.cah.android.game.vote

import android.util.Log
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.game.round.cardPaddings
import com.eleks.cah.android.game.round.dropShadow
import com.eleks.cah.android.theme.*
import com.eleks.cah.android.widgets.AnimatedValueVisibility
import com.eleks.cah.android.widgets.ConflictCard
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import com.eleks.cah.domain.model.QuestionCard
import com.eleks.cah.domain.model.RoundPlayerAnswer
import kotlinx.coroutines.delay


@Preview
@Composable
private fun ScoreScreenPreview() {
    MyApplicationTheme {
        ScoreScreen(
            QuestionCard("1", "test", listOf(0)),
            emptyList(),
            1,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreScreen(
    question: QuestionCard,
    answers: List<RoundPlayerAnswer>,
    roundNumber: Int,

    onTimeout: (List<RoundPlayerAnswer>) -> Unit = {},
    onVote: (List<RoundPlayerAnswer>) -> Unit = {}
) {

    var timeout by remember {
        mutableStateOf(60)
    }

    LaunchedEffect(key1 = "timeout") {
        while (timeout > 0) {
            delay(1000L)
            timeout--
        }
        onTimeout(answers)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {

        GameHeader(
            gameLabelSize = GameLabelSize.SMALL,
            headerHeight = AppTheme.dimens.headerSize
        )

        Timer(timeout)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(top = 33.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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

            Tabs(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
                cardsCount = answers.size,
                pagerState = pagerState,
            )

            Spacer(Modifier.weight(1f))

            val mutableAnswers = remember { answers.toMutableStateList() }

            ScoreCards(
                pagerState = pagerState,
                masterCard = question,
                answers = mutableAnswers,
            )

            Spacer(Modifier.weight(1f))

            val currentPage = pagerState.currentPage

            Scores(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp),
                selectedVote = mutableAnswers[currentPage].score,
            ) {
                Log.d("###", "selected vote = $it")
                mutableAnswers[currentPage] = mutableAnswers[currentPage].copy(score = it ?: 0)
                onVote(mutableAnswers)
            }
        }
    }
}

@Composable
private fun Timer(
    timeoutInSecs: Int = 60,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_timer),
            contentDescription = "",
            colorFilter = ColorFilter.tint(color = MainBlack)
        )

        val mins = timeoutInSecs / 60
        val minsStr = if (mins <= 9) {
            "0$mins"
        } else {
            "$mins"
        }
        var seconds = timeoutInSecs % 60
        val secondsStr = if (seconds <= 9) {
            "0$seconds"
        } else {
            "$seconds"
        }
        Text(text = "$minsStr:$secondsStr", Modifier.padding(start = 6.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Tabs(
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScoreCards(
    pagerState: PagerState,
    masterCard: QuestionCard,
    answers: List<RoundPlayerAnswer>
) {

    Box(modifier = Modifier, contentAlignment = Alignment.TopCenter) {
        ConflictCard(
            isMasterCard = true,
            cardText = masterCard.text,
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
    answers: List<RoundPlayerAnswer>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        pageCount = answers.size,
        state = pagerState,
        modifier = modifier
    ) {
        val card = answers[it]

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.wrapContentHeight()
        ) {

            val angle = if (it % 2 == 0) -15.0f else 15.0f

            ConflictCard(
                cardText = card.playerAnswers[0],
                modifier = Modifier.rotate(angle),
            )

            AnimatedValueVisibility(card) { vote ->

                val painterRes = when (vote.score) {
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
private fun Scores(
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