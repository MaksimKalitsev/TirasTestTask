package ua.zp.tirastesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.zp.tirastesttask.Config
import ua.zp.tirastesttask.data.models.ForecastData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.domain.location.LocationTracker
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: IWeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {


    private val _currentWeather = MutableLiveData<Result<WeatherData>>()
    val currentWeather: LiveData<Result<WeatherData>> = _currentWeather


    private val _forecast = MutableLiveData<Result<List<ForecastData>>>()
    val forecast: LiveData<Result<List<ForecastData>>> = _forecast

    fun fetchWeatherForCurrentLocation() = viewModelScope.launch {
        val location = locationTracker.getCurrentLocation()
        if (location != null) {
            val locationString = "${location.latitude},${location.longitude}"

            val currentWeatherResult = repository.getCurrentWeatherDay(Config.API_KEY, locationString)
            _currentWeather.value = currentWeatherResult

            val forecastResult = repository.getForecast(Config.API_KEY, locationString, 3)
            _forecast.value = forecastResult
        } else {
            _currentWeather.value = Result.failure(Exception("Unable to get location"))
            _forecast.value = Result.failure(Exception("Unable to get location"))
        }
    }
}
