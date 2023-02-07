package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eleks.cah.android.model.Card
import com.eleks.cah.android.round.PreRoundScreen
import com.eleks.cah.android.round.RoundScreen
import com.eleks.cah.init
import kotlinx.coroutines.delay

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
                        startDestination = Route.PreRoundScreen.path,
                    ) {
                        composable(Route.Menu.path) {
                            Menu(
                                onNavigationRequired = {
                                    navController.navigate(it.path)
                                },
                                onExit = { finish() }
                            )
                        }

                        composable(Route.NewGame.path) {
                            EnterNameScreen()
                        }

                        composable(Route.JoinGame.path) {
                            EnterCodeScreen(onNextClicked = {
                                navController.navigate(Route.NewGame.path)
                            })
                        }

                        composable(Route.Settings.path) {
                            Text("Settings")
                        }

                        composable(
                            route = Route.PreRoundScreen.path,
                            arguments = listOf(
                                navArgument("number") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            it.arguments?.getInt("number")?.let {
                                PreRoundScreen(roundNumber = it)

                                LaunchedEffect(Unit) {
                                    delay(ROUND_TIMEOUT)
                                    navController.navigate(Route.Round.path + "/$it")
                                }
                            }
                        }

                        composable(
                            Route.Round.path + "/{number}", arguments =
                            listOf(
                                navArgument("number") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            it.arguments?.getInt("number")?.let {
                                RoundScreen(
                                    listOf(
                                        Card(text = stringResource(id = R.string.miy_instrument)),
                                        Card(text = stringResource(id = R.string.miy_instrument)),
                                        Card(text = stringResource(id = R.string.miy_instrument)),
                                        Card(text = stringResource(id = R.string.miy_instrument)),
                                        Card(text = stringResource(id = R.string.miy_instrument)),
                                    ), it
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

    @Composable
    fun GreetingView(text: String) {
        Text(text = text)
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MyApplicationTheme {
            GreetingView("Hello, Android!")
        }
    }
}
