package ua.zp.tirastesttask.domain.repository

import ua.zp.tirastesttask.data.db.ForecastHourEntity
import ua.zp.tirastesttask.data.db.WeatherDataEntity
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.ForecastHourData
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

    suspend fun getAllWeatherData(): List<WeatherData>
    suspend fun insertWeatherData(weatherData: WeatherData)
    suspend fun getAllForecastDays(): List<ForecastDayData>
    suspend fun insertForecastDay(forecastDay: ForecastDayData)
    suspend fun getAllForecastHours(): List<ForecastHourData>
    suspend fun insertForecastHour(forecastHour: ForecastHourData, dayId: String)
    suspend fun clearForecastDayTable()
    suspend fun clearWeatherDataTable()
    suspend fun clearForecastHourTable()


}



