package ua.zp.tirastesttask.data.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ua.zp.tirastesttask.data.models.ForecastDayData
import ua.zp.tirastesttask.data.models.ForecastHourData
import ua.zp.tirastesttask.data.models.WeatherData

@Parcelize
data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
) : Parcelable {
    fun toWeatherData(): WeatherData =
        WeatherData(
            localTime = location.localtime,
            temperature = current.temp_c,
            cityName = location.name,
            humidity = current.humidity,
            feelsLike = current.feelslike_c,
            icon = current.condition.icon,
            lat = location.lat,
            lon = location.lon,
            windSpeed = current.wind_kph,
            condition = current.condition.text,
            pressure = current.pressure_mb
        )

}

@Parcelize
data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
) : Parcelable

@Parcelize
data class Current(
    val last_updated_epoch: Long,
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: Condition,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure_mb: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val humidity: Double,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val uv: Double,
    val gust_mph: Double,
    val gust_kph: Double
) : Parcelable

@Parcelize
data class Condition(
    val text: String,
    val icon: String,
    val code: Int
) : Parcelable

@Parcelize
data class Forecast(
    val forecastday: List<ForecastDay>
) : Parcelable {

}

@Parcelize
data class ForecastDay(
    val date: String,
    val date_epoch: Long,
    val day: Day,
    val astro: Astro,
    val hour: List<Hour>
) : Parcelable {
    fun toForecastData(): ForecastDayData =
        ForecastDayData(
            date = date,
            mintemp_c = day.mintemp_c,
            maxtemp_c = day.maxtemp_c,
            icon = day.condition.icon,
            condition = day.condition.text,
            hour = hour.map { it.toForecastHourData() }
        )
}

@Parcelize
data class Day(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val totalprecip_in: Double,
    val totalsnow_cm: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val avghumidity: Double,
    val daily_will_it_rain: Int,
    val daily_chance_of_rain: Int,
    val daily_will_it_snow: Int,
    val daily_chance_of_snow: Int,
    val condition: Condition,
    val uv: Double
) : Parcelable

@Parcelize
data class Astro(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: String,
    val is_moon_up: Int,
    val is_sun_up: Int
) : Parcelable

@Parcelize
data class Hour(
    val time_epoch: Long,
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: Condition,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure_mb: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val humidity: Double,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val windchill_c: Double,
    val windchill_f: Double,
    val heatindex_c: Double,
    val heatindex_f: Double,
    val dewpoint_c: Double,
    val dewpoint_f: Double,
    val will_it_rain: Int,
    val chance_of_rain: Int,
    val will_it_snow: Int,
    val chance_of_snow: Int,
    val vis_km: Double,
    val vis_miles: Double,
    val gust_mph: Double,
    val gust_kph: Double,
    val uv: Double
) : Parcelable{
    fun toForecastHourData():ForecastHourData =
        ForecastHourData(
            time = time,
            icon = condition.icon,
            humidity = humidity,
            pressure = pressure_mb,
            windSpeed = wind_kph,
            condition = condition.text,
            temperature = temp_c
        )
}


