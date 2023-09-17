package ua.zp.tirastesttask.repository

import ua.zp.tirastesttask.data.models.WeatherDataCurrentDay
import ua.zp.tirastesttask.data.network.Api
import javax.inject.Inject

interface IWeatherRepository {
    suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<WeatherDataCurrentDay>>
    suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherDataCurrentDay>
}

class WeatherRepository @Inject constructor(
    private val api: Api
) : IWeatherRepository {
    override suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<WeatherDataCurrentDay>> = try {
        val weatherResponse =
            api.getWeatherForecast(apiKey, location, countDays)
        val weatherForecastList = listOf(weatherResponse.toWeatherDataCurrentDay())
        Result.success(weatherForecastList)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherDataCurrentDay> = try {
        val weatherResponse = api.getWeatherCurrentDay(apiKey, location).toWeatherDataCurrentDay()
        Result.success(weatherResponse)
    } catch (ex: Exception){
        Result.failure(ex)
    }
}

