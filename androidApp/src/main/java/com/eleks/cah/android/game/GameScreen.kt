package com.eleks.cah.android.game

import android.util.Log
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
import com.eleks.cah.game.GameContract
import com.eleks.cah.game.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun GameScreen(
    roomId: String,
    gameViewModel: GameViewModel = koinViewModel {
        parametersOf(roomId)
    }
) {

    val innerNavController = rememberNavController()

    val game by gameViewModel.state.collectAsState()

    Log.d("###", "Round # ${game.room?.currentRound?.number}")

    val roundNumber = game.room?.currentRound?.number ?: -1

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
            PreRoundScreen(roundNumber = roundNumber)
        }

        composable(route = GameRoute.Round.path) {
            RoundScreen(cards = emptyList(), roundNumber = roundNumber) {

            }
        }

        composable(route = GameRoute.Voting.path) {
            VotingScreen(roundNumber = roundNumber)
        }
    }
}