package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.GameViewModel
import com.eleks.cah.init

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GameViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        startDestination = Route.Menu.path
                    ) {
                        composable(Route.Menu.path) {
                            Menu(
                                onNewGame = {
                                    navController.navigate(Route.NewGame.path)
                                },
                                onJoinGame = {
                                    navController.navigate(Route.Lobby.path)
                                },
                                onSettings = {
                                    navController.navigate(Route.Settings.path)
                                },
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Route.NewGame.path) {
                            CreateRoom()
                        }
                        composable(Route.Lobby.path) {
                            Column {
                                Text("Lobby")
                            }
                        }
                        composable(Route.Settings.path) {
                            Column {
                                Text("Settings")
                            }
                        }
                    }
                }
            }
        }
    }
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
