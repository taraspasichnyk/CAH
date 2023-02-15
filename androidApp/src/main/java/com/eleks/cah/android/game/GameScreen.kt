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
import com.eleks.cah.android.round.PreRoundScreen
import com.eleks.cah.android.round.RoundScreen
import com.eleks.cah.android.vote.VotingScreen
import com.eleks.cah.game.GameViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun GameScreen(gameViewModel: GameViewModel = koinViewModel()) {

    val innerNavController = rememberNavController()

    val game by gameViewModel.state.collectAsState()


    NavHost(
        navController = innerNavController,
        startDestination = GameRoute.PreRound.path,
        modifier = Modifier.background(MaterialTheme.colors.secondary)
    ) {
        val roundNumber = game.room?.currentRound?.number ?: -1

        composable(route = GameRoute.PreRound.path) {
            LaunchedEffect(Unit) {
                delay(ROUND_TIMEOUT)
                gameViewModel.startNewRound()
                innerNavController.navigate(GameRoute.Round.path)
            }
            PreRoundScreen(roundNumber = roundNumber)
        }

        composable(route = GameRoute.Round.path) {

            RoundScreen(cards = emptyList(), roundNumber = roundNumber)
        }

        composable(route = GameRoute.Voting.path) {
            VotingScreen(roundNumber = roundNumber)
        }
    }
}