package ua.zp.tirastesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.zp.tirastesttask.Config
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.domain.location.LocationTracker
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: IWeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _currentWeather = MutableLiveData<WeatherData>()
    val currentWeather: LiveData<WeatherData> = _currentWeather

    private var firstRun: Boolean = true

    private val _forecast = MutableLiveData<List<ForecastDayData>>()
    val forecast: LiveData<List<ForecastDayData>> = _forecast


    fun fetchWeatherForCurrentLocation() = viewModelScope.launch {
        val location = locationTracker.getCurrentLocation()
        if (location != null) {
            val locationString = "${location.latitude},${location.longitude}"
            fetchWeather(locationString)
        } else {

        }
    }

    fun fetchWeatherForCity(cityName: String) = viewModelScope.launch {
        fetchWeather(cityName)
    }

    private fun fetchWeather(location: String) {
        viewModelScope.launch {

            if (firstRun) {
                val weatherDeferred =
                    async { repository.getCurrentWeatherDay(Config.API_KEY, location) }
                val forecastDeferred =
                    async { repository.getForecast(Config.API_KEY, location, 3) }

                applyResults(weatherDeferred.await(), forecastDeferred.await())
                firstRun = false
            } else {
                getWeatherDb()
                getForecastDb()
            }


        }
    }

    private suspend fun applyResults(
        weatherResult: Result<WeatherData>,
        forecastResult: Result<List<ForecastDayData>>
    ) {
        weatherResult.onSuccess { weather ->
            _currentWeather.value = weather
            insertWeather(weather)
        }.onFailure {
            getWeatherDb()
        }
        forecastResult.onSuccess { forecast ->
            _forecast.value = forecast
            insertForecast(forecast)
        }.onFailure {
            getForecastDb()
        }
    }

    private suspend fun getWeatherDb() = withContext(Dispatchers.IO) {
        val dbWeatherData = repository.getAllWeatherData()
        if (dbWeatherData.isNotEmpty()) {
            _currentWeather.postValue(dbWeatherData.first())
        }
    }


    private suspend fun getForecastDb() = withContext(Dispatchers.IO)  {
            val dbForecastData = repository.getAllForecastDays()
            if (dbForecastData.isNotEmpty()) {
                _forecast.postValue(dbForecastData)
            }

    }

    private suspend fun insertWeather(weather: WeatherData) = withContext(Dispatchers.IO) {
        repository.insertWeatherData(weather)
    }

    private suspend fun insertForecast(forecast: List<ForecastDayData>) =
        withContext(Dispatchers.IO) {
            forecast.forEach { forecastDay ->
                repository.insertForecastDay(forecastDay)
                forecastDay.hour.forEach { forecastHourData ->
                    repository.insertForecastHour(forecastHourData, forecastDay.date)
                }
            }
        }
}
