package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.game.GameViewModel
import com.eleks.cah.init
import com.eleks.cah.menu.MenuContract
import com.eleks.cah.menu.MenuViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val menuViewModel by viewModels<MenuViewModel>()

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

                    LaunchedEffect(key1 = "effects") {
                        menuViewModel.effect.collectLatest {
                            when (it) {
                                is MenuContract.Effect.Navigation.NewGameScreen -> {
                                    navController.navigate(Route.NewGame.path)
                                }
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = Route.Menu.path
                    ) {
                        composable(Route.Menu.path) {
                            Menu(
                                onNewGame = {
                                    menuViewModel.setEvent(MenuContract.Event.StartNewGame)
                                },
                                onJoinGame = {
                                },
                                onSettings = {
                                },
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(Route.NewGame.path) {
                            CreateRoom()
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
