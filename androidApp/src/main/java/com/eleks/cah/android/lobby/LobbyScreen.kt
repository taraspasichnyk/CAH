package com.eleks.cah.android.lobby

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.eleks.cah.android.R
import com.eleks.cah.android.router.LobbyRoute
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.android.widgets.animatedComposable
import com.eleks.cah.lobby.LobbyContract.Effect.*
import com.eleks.cah.lobby.LobbyViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LobbyScreen(lobbyViewModel: LobbyViewModel, navController: NavHostController) {
    BackHandler { lobbyViewModel.onBackPressed() }

    val context = LocalContext.current
    val innerNavController = rememberAnimatedNavController()
    LaunchedEffect(key1 = Unit) {
        lobbyViewModel.effect.collectLatest { effect ->
            when (effect) {
                is CopyCode -> {
                    val clipBoard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("CAH code", effect.code)
                    clipBoard.setPrimaryClip(clipData)

                    Toast.makeText(
                        context,
                        context.getString(R.string.message_code_copied),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ShareCode -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, effect.code)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }

                is Navigation.GameScreen -> {
                    navController.popBackStack(route = MainRoute.Menu(), inclusive = false)
                    navController.navigate(
                        MainRoute.Game.getPath(effect.roomId, effect.playerID)
                    )
                }

                is Navigation.MenuScreen -> {
                    navController.popBackStack()
                }

                Navigation.EnterCodeScreen -> {
                    if (!innerNavController.popBackStack(
                            route = LobbyRoute.EnterCode.path,
                            inclusive = false
                        )
                    ) {
                        innerNavController.navigate(LobbyRoute.EnterCode.path)
                    }
                }

                Navigation.EnterNameScreen -> {
                    if (!innerNavController.popBackStack(
                            route = LobbyRoute.EnterName.path,
                            inclusive = false
                        )
                    ) {
                        innerNavController.navigate(LobbyRoute.EnterName.path)
                    }
                }

                Navigation.UsersListScreen -> {
                    innerNavController.navigate(LobbyRoute.UserList.path)
                }

                is ShowError -> {
                    Toast.makeText(
                        context,
                        effect.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    AnimatedNavHost(
        navController = innerNavController,
        startDestination =
        if (lobbyViewModel.gameOwner) LobbyRoute.EnterName.path
        else LobbyRoute.EnterCode.path,
        modifier = Modifier.background(MaterialTheme.colors.secondary)
    ) {
        animatedComposable(route = LobbyRoute.EnterCode.path) {
            EnterCode(lobbyViewModel)
        }
        animatedComposable(route = LobbyRoute.EnterName.path) {
            EnterName(lobbyViewModel)
        }
        animatedComposable(route = LobbyRoute.UserList.path) {
            UserList(lobbyViewModel)
        }
    }
}