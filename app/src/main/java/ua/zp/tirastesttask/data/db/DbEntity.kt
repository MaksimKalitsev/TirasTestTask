package ua.zp.tirastesttask.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_day")
data class ForecastDayEntity(
    @PrimaryKey val id: Long,
    val localTime: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val cityName: String,
    val icon: String,
    val condition: String,
    val pressure: Double,
    val lat: Double,
    val lon: Double,
    val feelsLike: Double
)

@Entity(tableName = "forecast_hour",
    foreignKeys = [ForeignKey(
        entity = ForecastDayEntity::class,
        parentColumns = ["id"],
        childColumns = ["dayId"],
        onDelete = ForeignKey.CASCADE
    )])
data class ForecastHourEntity(
    @PrimaryKey val id: Long,
    val time: String,
    val temperature: Double,
    val icon: String,
    val condition: String,
    val windSpeed: Double,
    val pressure: Double,
    val humidity: Double,
    val dayId: Long
)
