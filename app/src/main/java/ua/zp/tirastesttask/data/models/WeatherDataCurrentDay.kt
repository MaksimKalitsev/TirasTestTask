package ua.zp.tirastesttask.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDataCurrentDay(
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


