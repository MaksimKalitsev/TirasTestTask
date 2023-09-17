package ua.zp.tirastesttask.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.zp.tirastesttask.data.network.response.WeatherResponse

interface Api {

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): List<WeatherResponse>
}