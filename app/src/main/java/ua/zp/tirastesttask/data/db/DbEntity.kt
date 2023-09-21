package ua.zp.tirastesttask.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.ForecastHourData
import ua.zp.tirastesttask.data.models.WeatherData

@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
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
) {
    fun toDbWeatherData(): WeatherData =
        WeatherData(
            temperature = temperature,
            localTime = localTime,
            humidity = humidity,
            windSpeed = windSpeed,
            cityName = cityName,
            icon = icon,
            condition = condition,
            pressure = pressure,
            lat = lat,
            lon = lon,
            feelsLike = feelsLike
        )
}

@Entity(tableName = "forecast_day")
data class ForecastDayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val icon: String,
    val condition: String,
    val cityName: String
) {
    fun toDbForecastDay(forecastHours: List<ForecastHourEntity>): ForecastDayData =
        ForecastDayData(
            date = date,
            maxtemp_c = maxtemp_c,
            mintemp_c = mintemp_c,
            icon = icon,
            condition = condition,
            hour = forecastHours.map { it.toDbForecastHourData() }
        )
}

@Entity(
    tableName = "forecast_hour",
    foreignKeys = [ForeignKey(
        entity = ForecastDayEntity::class,
        parentColumns = ["id"],
        childColumns = ["dayId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ForecastHourEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val time: String,
    val temperature: Double,
    val icon: String,
    val condition: String,
    val windSpeed: Double,
    val pressure: Double,
    val humidity: Double,
    val dayId: Long
) {
    fun toDbForecastHourData(): ForecastHourData =
        ForecastHourData(
            time = time,
            temperature = temperature,
            icon = icon,
            condition = condition,
            windSpeed = windSpeed,
            pressure = pressure,
            humidity = humidity
        )
}
