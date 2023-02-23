package com.eleks.cah.android.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun GameScreen(
    roomId: String,
    playerId: String,
    onExit: () -> Unit,
    gameViewModel: GameViewModel = koinViewModel {
        parametersOf(roomId, playerId)
    }
) {

    val innerNavController = rememberNavController()
    var showBackConfirmationDialog by remember {
        mutableStateOf(false)
    }
    BackHandler(true) {
        showBackConfirmationDialog = true
    }

    if (showBackConfirmationDialog) {
        AlertDialog(
            title = {
                Text("Are you sure ?")
            },
            text = {
                Text("You won't be able to reconnect to ongoing game")
            },
            confirmButton = {
                Button(onClick = {
                    showBackConfirmationDialog = false
                    onExit()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showBackConfirmationDialog = false
                }) {
                    Text("No")
                }
            },
            onDismissRequest = {

            }
        )
    }

    val player by gameViewModel.me.collectAsState()
    val currentRound by gameViewModel.round.collectAsState()

    LaunchedEffect(Unit) {
        gameViewModel.effect.collectLatest {
            when (it) {
                is GameContract.Effect.Navigation.PreRound -> {
                    innerNavController.popBackStack()
                    innerNavController.navigate(GameRoute.PreRound.path)
                }
                is GameContract.Effect.Navigation.YourCards -> {
                    innerNavController.popBackStack()
                    innerNavController.navigate(GameRoute.YourCards.path)
                }
                is GameContract.Effect.Navigation.Round -> {
                    innerNavController.popBackStack()
                    innerNavController.navigate(GameRoute.Round.path)
                }
                is GameContract.Effect.Navigation.Voting -> {
                    innerNavController.popBackStack()
                    innerNavController.navigate(GameRoute.Voting.path)
                }
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
            val round = currentRound ?: return@composable
            PreRoundScreen(roundNumber = round.number)
        }

        composable(route = GameRoute.YourCards.path) {
            val me = player ?: return@composable
            UserCardsScreen(me.cards) {
                gameViewModel.showRound()
            }
        }

        composable(route = GameRoute.Round.path) {
            val me = player ?: return@composable
            val round = currentRound ?: return@composable
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            gameViewModel.showYourCards()
                        }
                    ) {
                        Image(
                            painterResource(id = com.eleks.cah.android.R.drawable.ic_card_fab),
                            contentDescription = ""
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    RoundScreen(
                        me,
                        round,
                    ) {
                        gameViewModel.saveAnswers(it)
                    }
                }
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