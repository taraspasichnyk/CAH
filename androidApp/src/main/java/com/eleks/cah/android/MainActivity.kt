package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eleks.cah.android.lobby.LobbyScreen
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.round.PreRoundScreen
import com.eleks.cah.android.round.RoundScreen
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.init
import com.eleks.cah.lobby.LobbyViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setStatusBarLight()
        init()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = MainRoute.Menu(),
                    ) {
                        composable(route = MainRoute.Menu()) {
                            Menu(
                                onNavigationRequired = {
                                    navController.navigate(it)
                                },
                                onExit = { finish() }
                            )
                        }

                        composable(
                            route = MainRoute.Lobby(),
                            arguments = listOf(
                                navArgument(MainRoute.Lobby.arguments.first()) {
                                    type = NavType.BoolType
                                }
                            )
                        ) {
                            val createNewGame =
                                it.arguments?.getBoolean(MainRoute.Lobby.arguments.first()) ?: false
                            LobbyScreen(
                                getViewModel<LobbyViewModel>().apply {
                                    gameOwner = createNewGame
                                },
                                navController
                            )
                        }

                        composable(
                            route = MainRoute.PreRoundScreen(),
                            arguments = listOf(
                                navArgument(MainRoute.PreRoundScreen.arguments.first()) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            it.arguments?.getInt(MainRoute.PreRoundScreen.arguments.first())
                                ?.let { round ->
                                    PreRoundScreen(roundNumber = round)

                                    LaunchedEffect(Unit) {
                                        delay(ROUND_TIMEOUT)
                                        navController.navigate(MainRoute.Round.getPath(round))
                                    }
                                }
                        }

                        composable(
                            route = MainRoute.Round(),
                            arguments = listOf(
                                navArgument(MainRoute.Round.arguments.first()) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            it.arguments?.getInt(MainRoute.Round.arguments.first())
                                ?.let { round ->
                                    RoundScreen(
                                        listOf(
                                            Card(text = stringResource(id = R.string.miy_instrument)),
                                            Card(text = stringResource(id = R.string.miy_instrument)),
                                            Card(text = stringResource(id = R.string.miy_instrument)),
                                            Card(text = stringResource(id = R.string.miy_instrument)),
                                            Card(text = stringResource(id = R.string.miy_instrument)),
                                        ), round
                                    )
                                }
                        }
                    }
                }
            }
        }
    }

    private fun setStatusBarLight(light: Boolean = true) {
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = light
    }
}
