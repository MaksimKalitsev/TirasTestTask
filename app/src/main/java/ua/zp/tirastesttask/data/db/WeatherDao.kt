package ua.zp.tirastesttask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data")
    suspend fun getAllWeatherData(): List<WeatherDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherDataEntity)

    @Query("SELECT * FROM forecast_day")
    suspend fun getAllForecastDays(): List<ForecastDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastDay(forecastDay: ForecastDayEntity)

    @Query("SELECT * FROM forecast_hour")
    suspend fun getAllForecastHours(): List<ForecastHourEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastHour(forecastHour: ForecastHourEntity)

    @Query("SELECT * FROM forecast_hour WHERE dayId = :dayId")
    suspend fun getForecastHoursForDay(dayId: String): List<ForecastHourEntity>

    @Query("DELETE FROM forecast_day")
    suspend fun clearForecastDayTable()

    @Query("DELETE FROM weather_data")
    suspend fun clearWeatherDataTable()

    @Query("DELETE FROM forecast_hour")
    suspend fun clearForecastHourTable()


}