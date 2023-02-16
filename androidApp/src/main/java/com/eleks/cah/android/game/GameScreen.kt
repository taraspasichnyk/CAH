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
import com.eleks.cah.android.ROUND_TIMEOUT
import com.eleks.cah.android.game.round.PreRoundScreen
import com.eleks.cah.android.game.round.RoundScreen
import com.eleks.cah.android.game.vote.ScoreScreen
import com.eleks.cah.android.router.GameRoute
import com.eleks.cah.game.GameContract
import com.eleks.cah.game.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
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


        composable(route = GameRoute.PreRound.path) {
            LaunchedEffect(Unit) {
                delay(ROUND_TIMEOUT)
                innerNavController.navigate(GameRoute.Round.path)
            }
            PreRoundScreen(roundNumber = currentRound.number)
        }

        composable(route = GameRoute.Round.path) {

            val player = game.room?.players?.firstOrNull() ?: return@composable

            RoundScreen(
                player,
                currentRound,
            ) {
                gameViewModel.answer(it)
            }
        }

        composable(route = GameRoute.Voting.path) {
            ScoreScreen(
                currentRound.question,
                currentRound.answers,
                roundNumber = currentRound.number
            )
        }
    }
}