package ua.zp.tirastesttask.data.repository

import ua.zp.tirastesttask.data.db.AppDatabase
import ua.zp.tirastesttask.data.db.ForecastDayEntity
import ua.zp.tirastesttask.data.db.ForecastHourEntity
import ua.zp.tirastesttask.data.db.WeatherDataEntity
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.data.network.Api
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api, private val database: AppDatabase
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

    override suspend fun getAllWeatherData(): List<WeatherDataEntity> {
        return database.weatherDao().getAllWeatherData()
    }

    override suspend fun insertWeatherData(weatherData: WeatherDataEntity) {
        database.weatherDao().insertWeatherData(weatherData)
    }

    override suspend fun getAllForecastDays(): List<ForecastDayEntity> {
        return database.weatherDao().getAllForecastDays()
    }

    override suspend fun insertForecastDay(forecastDay: ForecastDayEntity) {
        database.weatherDao().insertForecastDay(forecastDay)
    }

    override suspend fun getAllForecastHours(): List<ForecastHourEntity> {
        return database.weatherDao().getAllForecastHours()
    }

    override suspend fun insertForecastHour(forecastHour: ForecastHourEntity) {
        database.weatherDao().insertForecastHour(forecastHour)
    }
}
