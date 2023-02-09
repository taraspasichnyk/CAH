package com.eleks.cah.android

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.round.ConflictCard
import com.eleks.cah.android.theme.RoseWhite40
import com.eleks.cah.android.theme.labelLarge
import com.eleks.cah.android.widgets.GameHeader
import kotlinx.coroutines.launch

@Preview
@Composable
private fun VotingScreenPreview() {
    MyApplicationTheme {
        VotingScreen()
    }
}

@Composable
fun VotingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        GameHeader()

        Text(
            text = "Round 1 Голосування",
            style = labelLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        InstaVotingHeader()

        InstaVotingCards(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            cards = listOf()
        )
    }
}

@Composable
private fun InstaVotingHeader(
    modifier: Modifier = Modifier
) {
    LazyRow(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        items(4) {
            Box(
                modifier
                    .width(30.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(RoseWhite40)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun InstaVotingCards(
    cards: List<Card>,
    modifier: Modifier = Modifier
) {

    val state = rememberPagerState()
    val scope = rememberCoroutineScope()

    HorizontalPager(
        pageCount = cards.size,
        state = state,
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) { page ->
        val scope = rememberCoroutineScope()
        CardPair()
    }
}

@Composable
private fun CardPair() {
    val scope = rememberCoroutineScope()

    Box {
        ConflictCard(
            cardText = stringResource(id = R.string.miy_instrument),
            modifier = Modifier
                .rotate(20f)
        )
        ConflictCard(
            cardText = stringResource(id = R.string.miy_instrument)
        )
    }
}