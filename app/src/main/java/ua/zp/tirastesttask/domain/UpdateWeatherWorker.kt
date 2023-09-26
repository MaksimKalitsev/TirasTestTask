package ua.zp.tirastesttask.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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

    override suspend fun doWork(): Result {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("weather_channel", "Weather Updates", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "weather_channel")
            .setContentTitle("Weather Update")
            .setContentText("Fetching weather data...")
            .setSmallIcon(R.drawable.ic_drop)

        notificationManager.notify(1, notificationBuilder.build())

        val location = locationTracker.getCurrentLocation() ?: return Result.failure()
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
                }

                forecastResult.onSuccess { forecast ->
                    forecast.forEach { forecastDay ->
                        repository.insertForecastDay(forecastDay)
                        forecastDay.hour.forEach { forecastHourData ->
                            repository.insertForecastHour(forecastHourData, forecastDay.date)
                        }
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {

            notificationBuilder.setContentText("Failed to fetch weather data")
            notificationManager.notify(1, notificationBuilder.build())

            e.printStackTrace()
            Result.failure()
        }
    }
}


