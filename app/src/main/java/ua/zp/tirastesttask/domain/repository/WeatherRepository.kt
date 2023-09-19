package ua.zp.tirastesttask.domain.repository

import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.WeatherData

interface IWeatherRepository {
    suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<ForecastDayData>>
    suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherData>
}



