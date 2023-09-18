package ua.zp.tirastesttask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.zp.tirastesttask.data.models.ForecastData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.data.repository.WeatherRepositoryImpl
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: IWeatherRepository) : ViewModel() {

    private val _currentWeather = MutableLiveData<Result<WeatherData>>()
    val currentWeather: LiveData<Result<WeatherData>> = _currentWeather

    private val _forecastWeather = MutableLiveData<Result<List<ForecastData>>>()
    val forecastWeather: LiveData<Result<List<ForecastData>>> = _forecastWeather

    fun getCurrentWeather(apiKey: String, location: String) {
        viewModelScope.launch {
            val result = repository.getCurrentWeatherDay(apiKey, location)
            _currentWeather.value = result
        }
    }

    fun getWeatherForecast(apiKey: String, location: String, countDays: Int) {
        viewModelScope.launch {
            val result = repository.getForecast(apiKey, location, countDays)
            _forecastWeather.value = result
        }
    }
}
