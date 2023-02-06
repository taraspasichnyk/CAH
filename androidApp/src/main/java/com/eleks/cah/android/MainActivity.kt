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
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.game.GameViewModel
import com.eleks.cah.init
import com.eleks.cah.menu.MenuContract
import com.eleks.cah.menu.MenuViewModel
import kotlinx.coroutines.flow.collectLatest

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
                        startDestination = Route.Menu.path
                    ) {
                        composable(Route.Menu.path) {
                            Menu(
                                onNavigationRequired = {
                                    navController.navigate(it.path)
                                },
                                onExit = { finish() }
                            )
                        }
                        composable(Route.NewGame.path){
                            EnterNameScreen()
                        }
                        composable(Route.JoinGame.path) {
                            EnterCodeScreen(onNextClicked ={
                                navController.navigate(Route.NewGame.path)
                            })
                        }
                        composable(Route.Settings.path) {
                            Text("Settings")
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
