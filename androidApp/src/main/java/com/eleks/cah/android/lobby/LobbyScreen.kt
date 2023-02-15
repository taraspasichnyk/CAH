package com.eleks.cah.android.lobby

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eleks.cah.android.R
import com.eleks.cah.android.router.LobbyRoute
import com.eleks.cah.android.router.MainRoute
import com.eleks.cah.lobby.LobbyContract.Effect.CopyCode
import com.eleks.cah.lobby.LobbyContract.Effect.Navigation
import com.eleks.cah.lobby.LobbyContract.Effect.ShareCode
import com.eleks.cah.lobby.LobbyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LobbyScreen(lobbyViewModel: LobbyViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val innerNavController = rememberNavController()
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
                is Navigation.YourCardsScreen -> {
                    //TODO navigate to needed screen
                    navController.popBackStack(route = MainRoute.Menu(), inclusive = false)
                    navController.navigate(MainRoute.PreRoundScreen.getPath(1))
                }
                is Navigation.MenuScreen -> {
                    navController.popBackStack()
                }
                Navigation.EnterCodeScreen -> {
                    innerNavController.navigate(LobbyRoute.EnterCode.path)
                }
                Navigation.EnterNameScreen -> {
                    innerNavController.navigate(LobbyRoute.EnterName.path)
                }
                Navigation.UsersListScreen -> {
                    innerNavController.navigate(LobbyRoute.UserList.path)
                }
            }
        }
    }

    NavHost(
        navController = innerNavController,
        startDestination =
        if (lobbyViewModel.gameOwner) LobbyRoute.EnterName.path
        else LobbyRoute.EnterCode.path,
        modifier = Modifier.background(MaterialTheme.colors.secondary)
    ) {
        composable(route = LobbyRoute.EnterCode.path) {
            EnterCode(lobbyViewModel)
        }
        composable(route = LobbyRoute.EnterName.path) {
            EnterName(lobbyViewModel)
        }
        composable(route = LobbyRoute.UserList.path) {
            UserList(lobbyViewModel)
        }
    }
}