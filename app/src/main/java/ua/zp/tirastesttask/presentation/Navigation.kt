package ua.zp.tirastesttask.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.zp.tirastesttask.presentation.detailsscreen.DetailsScreen
import ua.zp.tirastesttask.presentation.startscreen.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val viewModel = hiltViewModel<WeatherViewModel>()

    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(Screen.StartScreen.route) {
            StartScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Screen.DetailsScreen.route) {navBackStackEntry ->
            val dataKey = navBackStackEntry.arguments?.getString("dataKey")
            val forecastHourData = viewModel.forecast.value?.find { it.date == dataKey }?.hour
            if (forecastHourData != null) {
                DetailsScreen(forecastHourData = forecastHourData)
            }
        }
    }
}

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object DetailsScreen : Screen("details_screen/{dataKey}")
}