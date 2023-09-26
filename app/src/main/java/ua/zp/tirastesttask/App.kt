package ua.zp.tirastesttask

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.hilt.android.HiltAndroidApp
import ua.zp.tirastesttask.domain.IScheduleManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App: Application() , Configuration.Provider {

    @Inject
    lateinit var configuration: Configuration

    @Inject
    lateinit var scheduleManager: IScheduleManager

     override fun getWorkManagerConfiguration() = configuration
    override fun onCreate() {
        super.onCreate()

        scheduleManager.scheduleWeatherUpdates()

    }
}