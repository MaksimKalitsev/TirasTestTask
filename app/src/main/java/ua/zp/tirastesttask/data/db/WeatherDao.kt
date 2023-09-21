package ua.zp.tirastesttask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM forecast_day")
    fun getAllForecastDays(): List<ForecastDayEntity>

    @Query("SELECT * FROM forecast_hour WHERE dayId = :dayId")
    fun getForecastHoursForDay(dayId: Long): List<ForecastHourEntity>

    @Insert
    fun insertForecastDay(forecastDay: ForecastDayEntity)

    @Insert
    fun insertForecastHours(forecastHours: List<ForecastHourEntity>)
}