package ua.zp.tirastesttask.presentation.startscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import ua.zp.tirastesttask.presentation.ForecastCard
import ua.zp.tirastesttask.presentation.Screen
import ua.zp.tirastesttask.presentation.SearchBar
import ua.zp.tirastesttask.presentation.WeatherCard
import ua.zp.tirastesttask.presentation.WeatherViewModel
import ua.zp.tirastesttask.presentation.ui.theme.DarkBlue
import ua.zp.tirastesttask.presentation.ui.theme.DeepBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(viewModel: WeatherViewModel, navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (searchBar, weatherCard) = createRefs()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .constrainAs(searchBar){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(weatherCard.top)
                }
                .constrainAs(weatherCard){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(searchBar.bottom)
                }
        ) {
            SearchBar(
                onSearchCompleted = { cityName ->
                    viewModel.fetchWeatherForCity(cityName)
                },
            )

            val currentWeatherResult by viewModel.currentWeather.observeAsState()
            val forecastResult by viewModel.forecast.observeAsState()

            currentWeatherResult?.let { result ->
                WeatherCard(
                    data = result,
                    backgroundColor = DeepBlue
                )
            }
            forecastResult?.let { result ->
                if (result.isNotEmpty()) {
                    LazyColumn {
                        items(result) { forecastData ->
                            ForecastCard(
                                data = forecastData,
                                backgroundColor = DeepBlue,
                                onClick = {
                                    val route = Screen.DetailsScreen.route.replace(
                                        "{dataKey}",
                                        forecastData.date
                                    )
                                    navController.navigate(route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}