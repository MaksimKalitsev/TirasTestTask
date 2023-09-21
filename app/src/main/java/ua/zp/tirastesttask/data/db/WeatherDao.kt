package ua.zp.tirastesttask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data")
    suspend fun getAllWeatherData(): List<WeatherDataEntity>

    @Insert
    suspend fun insertWeatherData(weatherData: WeatherDataEntity)

    @Query("SELECT * FROM forecast_day")
    suspend fun getAllForecastDays(): List<ForecastDayEntity>

    @Insert
    suspend fun insertForecastDay(forecastDay: ForecastDayEntity)

    @Query("SELECT * FROM forecast_hour")
    suspend fun getAllForecastHours(): List<ForecastHourEntity>

    @Insert
    suspend fun insertForecastHour(forecastHour: ForecastHourEntity)


}