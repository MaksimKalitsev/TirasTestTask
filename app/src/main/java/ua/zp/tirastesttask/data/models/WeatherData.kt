package ua.zp.tirastesttask.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ua.zp.tirastesttask.data.db.ForecastDayEntity
import ua.zp.tirastesttask.data.db.ForecastHourEntity
import ua.zp.tirastesttask.data.db.WeatherDataEntity

@Parcelize
data class WeatherData(
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
) : Parcelable {
    fun toDbWeatherEntity():WeatherDataEntity =
        WeatherDataEntity(
            cityName = cityName,
            icon = icon,
            localTime = localTime,
            temperature = temperature,
            humidity = humidity,
            windSpeed = windSpeed,
            condition = condition,
            pressure = pressure,
            lat = lat,
            lon = lon,
            feelsLike = feelsLike
        )
}

@Parcelize
data class ForecastDayData(
    val date: String,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val icon: String,
    val condition: String,
    val hour: List<ForecastHourData>
) : Parcelable{
    fun toDbForecastDayEntity(): ForecastDayEntity =
        ForecastDayEntity(
            date = date,
            maxtemp_c = maxtemp_c,
            mintemp_c = mintemp_c,
            icon = icon,
            condition = condition
        )
}

@Parcelize
data class ForecastHourData(
    val time: String,
    val temperature: Double,
    val icon: String,
    val condition: String,
    val windSpeed: Double,
    val pressure: Double,
    val humidity: Double
) : Parcelable {
    fun toDbForecastHourEntity(dayId: String): ForecastHourEntity =
        ForecastHourEntity(
            time = time,
            temperature = temperature,
            icon = icon,
            condition = condition,
            windSpeed = windSpeed,
            pressure = pressure,
            humidity = humidity,
            dayId = dayId
        )
}


