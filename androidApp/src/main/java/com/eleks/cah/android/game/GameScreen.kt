package com.eleks.cah.android.game

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.eleks.cah.game.GameContract
import com.eleks.cah.game.GameViewModel
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

    val game by gameViewModel.state.collectAsState()

    val currentRound = game.room?.currentRound ?: return

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
            Text("My Cards here")
        }

        composable(route = GameRoute.PreRound.path) {
            PreRoundScreen(roundNumber = currentRound.number)
        }

        composable(route = GameRoute.Round.path) {

            val player = game.room?.players?.firstOrNull() ?: return@composable

            RoundScreen(
                player,
                currentRound,
            ) {
                gameViewModel.saveAnswers(it)
            }
        }

        composable(route = GameRoute.Voting.path) {
            ScoreScreen(
                currentRound.masterCard,
                currentRound.playerCards,
                roundNumber = currentRound.number
            ) {
                gameViewModel.saveScores(it)
            }
        }
    }
}