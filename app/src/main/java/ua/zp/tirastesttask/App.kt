package ua.zp.tirastesttask

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import ua.zp.tirastesttask.domain.IScheduleManager
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