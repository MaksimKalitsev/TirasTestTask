package ua.zp.tirastesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _currentWeather = MutableLiveData<Result<WeatherData>>()
    val currentWeather: LiveData<Result<WeatherData>> = _currentWeather


    private val _forecast = MutableLiveData<Result<List<ForecastDayData>>>()
    val forecast: LiveData<Result<List<ForecastDayData>>> = _forecast


    fun fetchWeatherForCurrentLocation() = viewModelScope.launch {
        val location = locationTracker.getCurrentLocation()
        if (location != null) {
            val locationString = "${location.latitude},${location.longitude}"
            fetchWeather(locationString)
        } else {
            _currentWeather.value = Result.failure(Exception("Unable to get location"))
            _forecast.value = Result.failure(Exception("Unable to get location"))
        }
    }

    fun fetchWeatherForCity(cityName: String) = viewModelScope.launch {
        fetchWeather(cityName)
    }

    private fun fetchWeather(location: String) {
        viewModelScope.launch {
            val apiWeather = withContext(Dispatchers.IO) {
                repository.getCurrentWeatherDay(Config.API_KEY, location)
            }
            val apiForecast =
                withContext(Dispatchers.IO) {
                    repository.getForecast(Config.API_KEY, location, 3) }

            if (apiWeather.isSuccess && apiForecast.isSuccess) {
                _currentWeather.postValue(apiWeather)
                _forecast.postValue(apiForecast)

                apiWeather.getOrNull()?.let {
                    repository.insertWeatherData(it)
                }

                apiForecast.getOrNull()?.let { forecastList ->
                    forecastList.forEach { forecastDay ->
                        repository.insertForecastDay(forecastDay)
                        forecastDay.hour.forEach { forecastHourData ->
                            repository.insertForecastHour(forecastHourData, forecastDay.date)
                        }
                    }

                }
            } else {
                val dbWeatherData = repository.getAllWeatherData()
                val dbForecastData = repository.getAllForecastDays()

                if (dbWeatherData.isNotEmpty()) {
                    _currentWeather.postValue(Result.success(dbWeatherData.first()))
                }

                if (dbForecastData.isNotEmpty()) {
                    _forecast.postValue(Result.success(dbForecastData))
                }
            }
        }
    }
}
