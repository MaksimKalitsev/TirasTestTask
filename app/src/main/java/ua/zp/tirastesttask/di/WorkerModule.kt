package ua.zp.tirastesttask.di

import android.app.NotificationManager
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.zp.tirastesttask.UpdateWeatherWorker
import ua.zp.tirastesttask.domain.location.LocationTracker
import ua.zp.tirastesttask.domain.repository.IWeatherRepository

@InstallIn(SingletonComponent::class)
@Module
object WorkerModule {

    @Provides
    fun provideWorkerFactory(
        repository: IWeatherRepository,
        locationTracker: LocationTracker,
        notificationManager: NotificationManager
    ): WorkerFactory {
        return object : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker? {
                return UpdateWeatherWorker(appContext, workerParameters, repository, locationTracker, notificationManager)
            }
        }
    }

    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context
    ) = WorkManager.getInstance(context)

    @Provides
    fun provideConfiguration(
        workerFactory: WorkerFactory
    ) = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    @Provides
    fun provideNotificationManager(appContext: Context): NotificationManager {
        return appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}
