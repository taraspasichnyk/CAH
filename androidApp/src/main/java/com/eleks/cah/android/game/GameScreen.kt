package com.eleks.cah.android.game

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.android.*
import com.eleks.cah.android.R
import com.eleks.cah.android.game.results.RoundResultsScreen
import com.eleks.cah.android.game.round.PreRoundScreen
import com.eleks.cah.android.game.round.RoundScreen
import com.eleks.cah.android.game.user_cards.UserCardsScreen
import com.eleks.cah.android.game.vote.ScoreScreen
import com.eleks.cah.android.router.GameRoute
import com.eleks.cah.android.widgets.animatedComposable
import com.eleks.cah.domain.model.AnswerCardID
import com.eleks.cah.domain.model.GameRound
import com.eleks.cah.domain.model.Player
import com.eleks.cah.domain.model.RoundPlayerAnswer
import com.eleks.cah.game.GameContract
import com.eleks.cah.game.GameViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    roomId: String,
    playerId: String,
    onExit: () -> Unit,
    gameViewModel: GameViewModel = koinViewModel {
        parametersOf(roomId, playerId)
    }
) {

    val innerNavController = rememberAnimatedNavController()

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

    val state by gameViewModel.state.collectAsState()

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
                is GameContract.Effect.Navigation.Results -> {
                    innerNavController.popBackStack()
                    innerNavController.navigate(GameRoute.Results.path)
                }
                else -> {}
            }
        }
    }

    GameContainer(
        innerNavController,
        state.round,
        state.me,
        state.players,
        onFabClicked = {
            gameViewModel.showYourCards()
        },
        onUserCardsDismissed = {
            gameViewModel.showRound()
        },
        onAnswerSubmitted = {
            gameViewModel.saveAnswers(it)
        },
        onScoreSubmitted = {
            gameViewModel.saveScores(it)
        },
        onLeaderboardViewed = {
            if (state.round != null) {
                gameViewModel.showYourCards()
            } else {
                onExit()
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun GameContainer(
    innerNavController: NavHostController,
    currentRound: GameRound?,
    player: Player?,
    allPlayers: List<Player>?,
    onFabClicked: () -> Unit = {},
    onUserCardsDismissed: () -> Unit = {},
    onAnswerSubmitted: (List<AnswerCardID>) -> Unit = {},
    onScoreSubmitted: (List<RoundPlayerAnswer>) -> Unit = { _ -> },
    onLeaderboardViewed: () -> Unit = {},
) {
    AnimatedNavHost(
        navController = innerNavController,
        startDestination = GameRoute.YourCards.path,
        modifier = Modifier.background(MaterialTheme.colors.secondary)
    ) {

        animatedComposable(route = GameRoute.PreRound.path) {
            val round = currentRound ?: return@animatedComposable
            PreRoundScreen(roundNumber = round.number)
        }

        animatedComposable(route = GameRoute.YourCards.path) {
            val me = player ?: return@animatedComposable
            UserCardsScreen(me.cards, onUserCardsDismissed)
        }

        animatedComposable(route = GameRoute.Round.path) {
            val me = player ?: return@animatedComposable
            val round = currentRound ?: return@animatedComposable

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { onFabClicked() }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_card_fab),
                            contentDescription = ""
                        )
                    }
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    RoundScreen(
                        me,
                        round,
                        onAnswerSubmitted
                    )
                }
            }
        }

        animatedComposable(route = GameRoute.Voting.path) {
            val round = currentRound ?: return@animatedComposable
            ScoreScreen(
                round.masterCard,
                round.answers.filter { it.playerID != player?.id },
                round.number,
                onVote = onScoreSubmitted
            )
        }

        animatedComposable(route = GameRoute.Results.path) {
            val players = allPlayers ?: return@composable
            RoundResultsScreen(
                currentRound,
                players,
                onNextPressed = onLeaderboardViewed
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun GamePreview() {
    MyApplicationTheme {
        GameContainer(
            innerNavController = rememberNavController(),
            currentRound = mockedRound(),
            player = mockedPlayer(),
            allPlayers = mockedGameRoom().players,
        )
    }
}