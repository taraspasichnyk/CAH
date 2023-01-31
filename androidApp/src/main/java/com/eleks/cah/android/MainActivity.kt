package com.eleks.cah.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eleks.cah.GameState
import com.eleks.cah.GameViewModel
import com.eleks.cah.getPlatform

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<GameViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val state by viewModel.state.collectAsState(initial = GameState.InMenu)

                    when (state) {
                        GameState.InMenu -> {
                            Column {
                                Text("New Game")
                                Text("Join Game")
                                Text("Settings")
                                Text("Exit")
                            }
                        }

                        GameState.InLobby -> TODO()
                        GameState.InRoomCreation -> TODO()
                        GameState.InSettings -> TODO()
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
