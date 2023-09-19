package ua.zp.tirastesttask

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ua.zp.tirastesttask.presentation.ForecastCard
import ua.zp.tirastesttask.presentation.WeatherCard
import ua.zp.tirastesttask.presentation.WeatherViewModel
import ua.zp.tirastesttask.presentation.ui.theme.DarkBlue
import ua.zp.tirastesttask.presentation.ui.theme.DeepBlue
import ua.zp.tirastesttask.presentation.ui.theme.TirasTestTaskTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val hasFineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val hasCoarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            if (hasFineLocation && hasCoarseLocation) {
                viewModel.fetchWeatherForCurrentLocation()
            } else {
                Toast.makeText(this, "No access permissions", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        setContent {
            TirasTestTaskTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                ) {
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
                                                backgroundColor = DeepBlue
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
        }
    }
}


