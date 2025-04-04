package com.example.mobileyavts

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobileyavts.ui.AppViewModelProvider
import com.example.mobileyavts.ui.item.*

@Composable
fun InventoryApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.FirstScreen.route
    ) {
        composable(Screen.FirstScreen.route) {
            val firstViewModel: FirstViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val settingsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
            FirstScreen(navController, firstViewModel, settingsViewModel)
        }
        composable(Screen.SettingsScreen.route) {
            val settingsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
            SettingsScreen(navController, settingsViewModel)
        }
        composable(
            route = Screen.WordEditScreen.route,
            arguments = listOf(navArgument("wordId") { type = NavType.IntType })
        ) { backStackEntry ->
            val wordId = backStackEntry.arguments?.getInt("wordId")
            if (wordId != null) {
                val application = LocalContext.current.applicationContext as InventoryApplication
                val repository = application.container.itemsRepository
                val wordEditViewModel: WordEditViewModel = viewModel(
                    factory = WordEditViewModelFactory(wordId, repository)
                )
                WordEditScreen(navController, wordEditViewModel)
            } else {
            }
        }
    }
}

sealed class Screen(val route: String) {
    object FirstScreen : Screen("FirstScreen")
    object SettingsScreen : Screen("SettingsScreen")
    object WordEditScreen : Screen("WordEditScreen/{wordId}") {
        fun createRoute(wordId: Int) = "WordEditScreen/$wordId"
    }
}
