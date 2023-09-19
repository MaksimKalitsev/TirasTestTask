package ua.zp.tirastesttask.data.repository

import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.data.network.Api
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api
) : IWeatherRepository {

    override suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<ForecastDayData>> = try {
        val weatherResponse =
            api.getWeatherForecast(apiKey, location, countDays)
        val weatherForecastList = weatherResponse.forecast.forecastday.map { it.toForecastData() }
        Result.success(weatherForecastList)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherData> = try {
        val weatherResponse = api.getWeatherCurrentDay(apiKey, location).toWeatherData()
        Result.success(weatherResponse)
    } catch (ex: Exception) {
        Result.failure(ex)
    }
}
