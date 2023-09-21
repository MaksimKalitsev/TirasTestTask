package ua.zp.tirastesttask.domain.repository

import androidx.room.Insert
import androidx.room.Query
import ua.zp.tirastesttask.data.db.ForecastDayEntity
import ua.zp.tirastesttask.data.db.ForecastHourEntity
import ua.zp.tirastesttask.data.db.WeatherDataEntity
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

    suspend fun getAllWeatherData(): List<WeatherDataEntity>
    suspend fun insertWeatherData(weatherData: WeatherDataEntity)
    suspend fun getAllForecastDays(): List<ForecastDayEntity>
    suspend fun insertForecastDay(forecastDay: ForecastDayEntity)
    suspend fun getAllForecastHours(): List<ForecastHourEntity>
    suspend fun insertForecastHour(forecastHour: ForecastHourEntity)

}



