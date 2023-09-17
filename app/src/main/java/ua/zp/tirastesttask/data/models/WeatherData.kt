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
    val lat: Double,
    val lon: Double,
    val feelsLike: Double
): Parcelable

@Parcelize
data class ForecastData(
    val date: String,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val icon: String
): Parcelable


