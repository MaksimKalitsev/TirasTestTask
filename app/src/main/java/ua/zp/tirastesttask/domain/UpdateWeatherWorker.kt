package ua.zp.tirastesttask.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ua.zp.tirastesttask.Config
import ua.zp.tirastesttask.R
import ua.zp.tirastesttask.domain.location.LocationTracker
import ua.zp.tirastesttask.domain.repository.IWeatherRepository


class UpdateWeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: IWeatherRepository,
    private val locationTracker: LocationTracker,
    private val notificationManager: NotificationManager
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val WEATHER_CHANNEL = "weather_channel"
    }

    private val notificationBuilder = NotificationCompat.Builder(applicationContext, WEATHER_CHANNEL)
        .setContentTitle("Weather Update")
        .setContentText("Fetching weather data...")
        .setSmallIcon(R.drawable.ic_drop)

    override suspend fun doWork(): Result {

        val channel = NotificationChannel(WEATHER_CHANNEL, "Weather Updates", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(1, notificationBuilder.build())

        val location = locationTracker.getCurrentLocation() ?: throw IllegalStateException("Unable to retrieve location")
        val locationString = "${location.latitude},${location.longitude}"
        return try {
            coroutineScope {
                val weatherDeferred = async { repository.getCurrentWeatherDay(Config.API_KEY, locationString) }
                val forecastDeferred = async { repository.getForecast(Config.API_KEY, locationString, 3) }

                val weatherResult = weatherDeferred.await()
                val forecastResult = forecastDeferred.await()

                weatherResult.onSuccess { weather ->

                    repository.insertWeatherData(weather)

                    val updatedText = "Temperature: ${weather.temperature}°C, Location: $locationString"
                    notificationBuilder.setContentText(updatedText)
                    notificationManager.notify(1, notificationBuilder.build())
                }.onFailure {
                    failure(it)
                }

                forecastResult.onSuccess { forecast ->
                    forecast.forEach { forecastDay ->
                        repository.insertForecastDay(forecastDay)
                        forecastDay.hour.forEach { forecastHourData ->
                            repository.insertForecastHour(forecastHourData, forecastDay.date)
                        }
                    }
                }.onFailure {
                    failure(it)
                }
            }

            Result.success()
        } catch (e: Exception) {
            failure(e)
        }
    }

    private fun failure(
        e: Throwable
    ): Result {
        notificationBuilder.setContentText("Failed to fetch weather data")
        notificationManager.notify(1, notificationBuilder.build())

        e.printStackTrace()
        return Result.failure()
    }
}


