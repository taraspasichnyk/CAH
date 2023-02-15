package com.eleks.cah.android.user_cards

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eleks.cah.android.AppTheme
import com.eleks.cah.android.MyApplicationTheme
import com.eleks.cah.android.R
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.theme.txtSemibold24
import com.eleks.cah.android.widgets.BlackButton
import com.eleks.cah.android.widgets.CardBackground
import com.eleks.cah.android.widgets.ConflictCard
import com.eleks.cah.android.widgets.GameHeader
import com.eleks.cah.android.widgets.GameLabelSize

@Composable
fun UserCardsScreen() {

    val cards: List<Card> = listOf(
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування"),
        Card("Божеволіти від нестримного програмування")
    )

    val userCards = remember {
        cards.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = txtSemibold24,
            color = Color.Black
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(COLUMN_COUNT),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.sizeSmall),
            contentPadding = PaddingValues(
                start = AppTheme.dimens.sizeMedium,
                end = AppTheme.dimens.sizeMedium,
                top = AppTheme.dimens.sizeMedium,
                bottom = AppTheme.dimens.sizeSmall
            ),
            modifier = Modifier.weight(1f)
        ) {
            items(userCards) {
                UserCardItemView(it)
            }
        }

        BlackButton(onClick = {
            //
        }, text = R.string.label_next)

        CardBackground(R.drawable.bg_pattern_big, modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(44.dp)
            )
        }
    }
}

@Composable
fun UserCardItemView(card: Card) {
    ConflictCard(
        cardText = card.text,
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