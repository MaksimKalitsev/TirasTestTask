package ua.zp.tirastesttask.data.models

data class WeatherDataCurrentDay(
    val localTime: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val pressure: Double,
    val cityName: String,
    val icon: String,
    val lat: Double,
    val lon: Double,
    val feelsLike: Double
)


