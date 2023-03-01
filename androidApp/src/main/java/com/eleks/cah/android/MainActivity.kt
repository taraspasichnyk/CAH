package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.*
import com.eleks.cah.android.game.GameScreen
import com.eleks.cah.android.lobby.LobbyScreen
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.android.widgets.animatedComposable
import com.eleks.cah.init
import com.eleks.cah.lobby.LobbyViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
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
                    val navController = rememberAnimatedNavController()

                    AnimatedNavHost(
                        navController = navController,
                        startDestination = MainRoute.Menu.path,
                    ) {
                        animatedComposable(route = MainRoute.Menu()) {
                            Menu(
                                onNavigationRequired = {
                                    navController.navigate(it)
                                },
                                onExit = { finish() }
                            )
                        }

                        animatedComposable(
                            route = MainRoute.Lobby(),
                            arguments = listOf(
                                navArgument(MainRoute.Lobby.arguments.first()) {
                                    type = NavType.BoolType
                                }
                            )
                        ) {
                            val createNewGame =
                                it.arguments?.getBoolean(MainRoute.Lobby.arguments.first())
                                    ?: false
                            LobbyScreen(
                                getViewModel<LobbyViewModel>().apply {
                                    gameOwner = createNewGame
                                },
                                navController
                            )
                        }

                        animatedComposable(route = MainRoute.Game()) {
                            val (roomIdKey, playerIdKey) = MainRoute.Game.arguments
                            val roomId = it.arguments?.getString(roomIdKey)
                                ?: return@animatedComposable
                            val playerId = it.arguments?.getString(playerIdKey)
                                ?: return@animatedComposable
                            GameScreen(roomId, playerId, onExit = {
                                navController.popBackStack()
                            })
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
