package ua.zp.tirastesttask.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.zp.tirastesttask.data.network.response.WeatherResponse

interface Api {
    @GET("current.json")
    suspend fun getWeatherCurrentDay(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") aqi: String = "no"
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "yes"
    ): WeatherResponse
}