package ua.zp.tirastesttask.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
) : Parcelable

@Parcelize
data class ForecastDayData(
    val date: String,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val icon: String,
    val condition: String,
    val hour: List<ForecastHourData>
) : Parcelable

@Parcelize
data class ForecastHourData(
    val time: String,
    val temperature: Double,
    val icon: String,
    val condition: String,
    val windSpeed: Double,
    val pressure: Double,
    val humidity: Double
) : Parcelable


