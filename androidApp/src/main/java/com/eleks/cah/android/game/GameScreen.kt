package com.eleks.cah.android.game

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.android.game.round.PreRoundScreen
import com.eleks.cah.android.game.round.RoundScreen
import com.eleks.cah.android.game.vote.ScoreScreen
import com.eleks.cah.android.router.GameRoute
import com.eleks.cah.android.user_cards.UserCardsScreen
import com.eleks.cah.game.GameContract
import com.eleks.cah.game.GameViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun GameScreen(
    roomId: String,
    playerId: String,
    gameViewModel: GameViewModel = koinViewModel {
        parametersOf(roomId, playerId)
    }
) {

    val innerNavController = rememberNavController()
    val player by gameViewModel.me.collectAsState()
    val currentRound by gameViewModel.round.collectAsState()

    LaunchedEffect(Unit) {
        gameViewModel.effect.collectLatest {
            when (it) {
                is GameContract.Effect.Navigation.PreRound -> innerNavController.navigate(GameRoute.PreRound.path)
                is GameContract.Effect.Navigation.YourCards -> innerNavController.navigate(GameRoute.YourCards.path)
                is GameContract.Effect.Navigation.Round -> innerNavController.navigate(GameRoute.Round.path)
                is GameContract.Effect.Navigation.Voting -> innerNavController.navigate(GameRoute.Voting.path)
                else -> {}
            }
        }
    }

    NavHost(
        navController = innerNavController,
        startDestination = GameRoute.PreRound.path,
        modifier = Modifier.background(MaterialTheme.colors.secondary)
    ) {

        composable(route = GameRoute.YourCards.path) {
            val me = player ?: return@composable
            UserCardsScreen(
                me.cards
            )
        }

        composable(route = GameRoute.PreRound.path) {
            val round = currentRound ?: return@composable
            PreRoundScreen(roundNumber = round.number)
        }

        composable(route = GameRoute.Round.path) {
            val me = player ?: return@composable
            val round = currentRound ?: return@composable
            RoundScreen(
                me,
                round,
            ) {
                gameViewModel.saveAnswers(it)
            }
        }

        composable(route = GameRoute.Voting.path) {
            val round = currentRound ?: return@composable
            ScoreScreen(
                round.masterCard,
                round.playerCards,
                round.number,
                onTimeout = { },
                onVote = { gameViewModel.saveScores(emptyList()) }
            )
        }
    }
}