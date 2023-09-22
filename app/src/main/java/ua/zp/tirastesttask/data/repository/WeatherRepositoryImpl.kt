package ua.zp.tirastesttask.data.repository


import ua.zp.tirastesttask.data.db.WeatherDao
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.ForecastHourData
import ua.zp.tirastesttask.data.models.WeatherData
import ua.zp.tirastesttask.data.network.Api
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: Api, private val weatherDao: WeatherDao
) : IWeatherRepository {

    override suspend fun getForecast(
        apiKey: String,
        location: String,
        countDays: Int
    ): Result<List<ForecastDayData>> = try {
        val weatherResponse =
            api.getWeatherForecast(apiKey, location, countDays)
        val weatherForecastList = weatherResponse.forecast.forecastday.map { it.toForecastData() }
        clearForecastDayTable()
        clearForecastHourTable()
        weatherForecastList.forEach { insertForecastDay(it) }
        Result.success(weatherForecastList)
    } catch (ex: Exception) {
        Result.failure(ex)
    }

    override suspend fun getCurrentWeatherDay(
        apiKey: String,
        location: String
    ): Result<WeatherData> = try {
        val weatherResponse = api.getWeatherCurrentDay(apiKey, location).toWeatherData()
        clearWeatherDataTable()
        insertWeatherData(weatherResponse)
        Result.success(weatherResponse)
    } catch (ex: Exception) {
        Result.failure(ex)
    }


    override suspend fun getAllWeatherData(): List<WeatherData> {
        val weatherDataEntities = weatherDao.getAllWeatherData()
        return weatherDataEntities.map { it.toWeatherData() }
    }

    override suspend fun insertWeatherData(weatherData: WeatherData) {
        val weatherDataEntity = weatherData.toDbWeatherEntity()
        weatherDao.insertWeatherData(weatherDataEntity)
    }

    override suspend fun getAllForecastDays(): List<ForecastDayData> {
        val forecastDayEntities = weatherDao.getAllForecastDays()
        return forecastDayEntities.map { forecastDayEntity ->
            val forecastHourEntities = weatherDao.getForecastHoursForDay(forecastDayEntity.date)
            forecastDayEntity.toForecastDay(forecastHourEntities )
        }
    }

    override suspend fun insertForecastDay(forecastDay: ForecastDayData) {
        val forecastDayEntity = forecastDay.toDbForecastDayEntity()
        weatherDao.insertForecastDay(forecastDayEntity)
    }

    override suspend fun getAllForecastHours(): List<ForecastHourData> {
        val forecastHourEntities = weatherDao.getAllForecastHours()
        return forecastHourEntities.map { it.toForecastHourData() }
    }

    override suspend fun insertForecastHour(forecastHour: ForecastHourData, dayId: String) {
        val forecastHourEntity = forecastHour.toDbForecastHourEntity(dayId)
        weatherDao.insertForecastHour(forecastHourEntity)
    }

    override suspend fun clearForecastDayTable() {
        weatherDao.clearForecastDayTable()
    }

    override suspend fun clearWeatherDataTable() {
        weatherDao.clearWeatherDataTable()
    }

    override suspend fun clearForecastHourTable() {
        weatherDao.clearForecastHourTable()
    }
}

