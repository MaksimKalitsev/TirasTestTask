package ua.zp.tirastesttask.domain

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface IScheduleManager {
    fun scheduleWeatherUpdates()
}

class ScheduleManager @Inject constructor(
    private val workManager: WorkManager
): IScheduleManager {
    override fun scheduleWeatherUpdates() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateWeatherWork = PeriodicWorkRequestBuilder<UpdateWeatherWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "UpdateWeather",
            ExistingPeriodicWorkPolicy.KEEP,
            updateWeatherWork
        )
    }
}