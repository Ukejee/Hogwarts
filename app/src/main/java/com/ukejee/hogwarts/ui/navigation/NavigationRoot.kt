package com.ukejee.hogwarts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ukejee.hogwarts.ui.characters.screens.CharacterDetailsScreen
import com.ukejee.hogwarts.ui.characters.screens.CharactersScreen
import com.ukejee.hogwarts.ui.characters.viewmodels.CharactersViewModel

@Composable
fun RootNavigationGraph(viewModel: CharactersViewModel, navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            CharactersScreen(viewModel) {
                navController.navigate(Screen.Details.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            }
        }
        composable(Screen.Details.route) {
            CharacterDetailsScreen(viewModel)
        }
    }
}


sealed class Screen(val route: String, val label: String) {
    data object Home : Screen(route = "home", label = "Home")
    data object Details : Screen(route = "details", label = "Character Details")
}

data class AppBarState(val title: String, val showBackButton: Boolean)