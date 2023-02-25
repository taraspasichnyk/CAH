package com.eleks.cah.android.user_cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.theme.txtSemibold24
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.ConflictCard
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize
import com.eleks.cah.domain.model.AnswerCard

@Composable
fun UserCardsScreen(
    cards: List<AnswerCard> = listOf(
        AnswerCard("0", "Божеволіти від нестримного програмування"),
        AnswerCard("1", "Божеволіти від нестримного програмування"),
        AnswerCard("2", "Божеволіти від нестримного програмування"),
        AnswerCard("3", "Божеволіти від нестримного програмування"),
        AnswerCard("4", "Божеволіти від нестримного програмування"),
        AnswerCard("5", "Божеволіти від нестримного програмування"),
        AnswerCard("6", "Божеволіти від нестримного програмування"),
        AnswerCard("7", "Божеволіти від нестримного програмування"),
        AnswerCard("8", "Божеволіти від нестримного програмування"),
        AnswerCard("9", "Божеволіти від нестримного програмування"),
    ),
    onActionPressed: () -> Unit = {}
) {

    val userCards = remember {
        cards.toMutableStateList()
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            GameHeader(
                gameLabelSize = GameLabelSize.SMALL, headerHeight = AppTheme.dimens.headerSize
            )

            Spacer(
                modifier = Modifier.height(AppTheme.dimens.sizeMedium)
            )

            Text(
                text = stringResource(id = R.string.user_cards),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = AppTheme.dimens.sizeSmall),
                style = txtSemibold24,
                color = Color.Black
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(COLUMN_COUNT),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.sizeSmall),
                contentPadding = PaddingValues(
                    start = AppTheme.dimens.sizeMedium,
                    end = AppTheme.dimens.sizeMedium,
                    top = AppTheme.dimens.sizeSmall,
                    bottom = AppTheme.dimens.sizeSmall
                ),
                modifier = Modifier.weight(1f)
            ) {
                items(userCards) {
                    UserCardItemView(it)
                }
            }

            Spacer(
                modifier = Modifier.defaultMinSize(
                    minHeight = dimensionResource(id = R.dimen.padding_80)
                )
            )

            CardBackground(
                R.drawable.bg_pattern_big, modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(dimensionResource(id = R.dimen.padding_44))
                )
            }
        }

        Box(
            contentAlignment = Alignment.CenterEnd, modifier = Modifier
                .padding(
                    end = AppTheme.dimens.sizeMedium,
                    bottom = dimensionResource(id = R.dimen.padding_70)
                )
                .align(Alignment.BottomEnd)
        ) {
            BlackButton(onClick = {
                onActionPressed()
            }, text = R.string.label_next)
        }
    }
}

@Composable
fun UserCardItemView(card: AnswerCard) {
    ConflictCard(
        cardText = card.answer,
        cardWidth = AppTheme.dimens.userCardWidth,
        cardHeight = AppTheme.dimens.userCardHeight
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun UserCardsScreenPreview() {
    MyApplicationTheme {
        UserCardsScreen()
    }
}

private const val COLUMN_COUNT = 3