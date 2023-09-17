package ua.zp.tirastesttask.domain.repository

import ua.zp.tirastesttask.data.models.ForecastData
import ua.zp.tirastesttask.data.models.WeatherData

interface IWeatherRepository {
    suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<ForecastData>>
    suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherData>
}



