package ua.zp.tirastesttask.presentation.startscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ua.zp.tirastesttask.presentation.ForecastCard
import ua.zp.tirastesttask.presentation.Screen
import ua.zp.tirastesttask.presentation.SearchBar
import ua.zp.tirastesttask.presentation.WeatherCard
import ua.zp.tirastesttask.presentation.WeatherViewModel
import ua.zp.tirastesttask.presentation.ui.theme.DarkBlue
import ua.zp.tirastesttask.presentation.ui.theme.DeepBlue

@Composable
fun StartScreen(viewModel: WeatherViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        var search by remember { mutableStateOf("") }
        SearchBar(search = search, onValueChange = {
            search = it
        },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp))

        val currentWeatherResult by viewModel.currentWeather.observeAsState()
        val forecastResult by viewModel.forecast.observeAsState()

        currentWeatherResult?.let { result ->
            when {
                result.isSuccess -> WeatherCard(
                    data = result.getOrNull()!!,
                    backgroundColor = DeepBlue
                )

                result.isFailure -> {

                }
            }
        }
        forecastResult?.let { result ->
            when {
                result.isSuccess -> {
                    val forecastDataList = result.getOrNull()
                    if (!forecastDataList.isNullOrEmpty()) {
                        LazyColumn {
                            items(forecastDataList) { forecastData ->
                                ForecastCard(
                                    data = forecastData,
                                    backgroundColor = DeepBlue,
                                    onClick = {
                                        val route = Screen.DetailsScreen.route.replace("{dataKey}", forecastData.date)
                                        navController.navigate(route)
                                    }
                                )
                            }
                        }
                    }
                }

                result.isFailure -> {

                }
            }
        }
    }
}