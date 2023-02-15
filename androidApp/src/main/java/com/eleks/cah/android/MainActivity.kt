package com.eleks.cah.android

import android.os.Bundle
import android.widget.Toast
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eleks.cah.android.game.GameScreen
import com.eleks.cah.android.lobby.LobbyScreen
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.round.PreRoundScreen
import com.eleks.cah.android.round.RoundScreen
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.android.vote.VotingScreen
import com.eleks.cah.domain.usecase.login.AnonymousLoginUseCase
import com.eleks.cah.init
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject
import org.koin.androidx.scope.scope
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val anonymousLoginUseCase: AnonymousLoginUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            anonymousLoginUseCase.invoke()
        }
        setStatusBarLight()
        init()
        setContent {
            MyApplicationTheme {
                LaunchedEffect(key1 = "") {

                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = MainRoute.Menu.path,
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
                                getViewModel(parameters = { parametersOf(createNewGame) }),
                                navController
                            )
                        }

                        composable(
                            route = MainRoute.Game.path,
                        ) {
                            GameScreen()
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
