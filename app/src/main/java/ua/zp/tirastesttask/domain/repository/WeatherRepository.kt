package ua.zp.tirastesttask.domain.repository


import kotlinx.coroutines.flow.Flow
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

    suspend fun getAllWeatherData(): Result<List<WeatherData>>
    suspend fun insertWeatherData(weatherData: WeatherData)
    suspend fun getAllForecastDays(): Result<List<ForecastDayData>>
    suspend fun insertForecastDay(forecastDay: ForecastDayData)
    suspend fun getAllForecastHours(): Result<List<ForecastHourData>>
    suspend fun insertForecastHour(forecastHour: ForecastHourData, dayId: String)
    suspend fun clearForecastDayTable()
    suspend fun clearWeatherDataTable()
    suspend fun clearForecastHourTable()


}



