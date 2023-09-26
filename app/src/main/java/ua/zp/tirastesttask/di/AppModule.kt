package ua.zp.tirastesttask.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.tirastesttask.domain.IScheduleManager
import ua.zp.tirastesttask.domain.ScheduleManager

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {
        @Binds
        fun bindScheduleManager(impl: ScheduleManager): IScheduleManager
    }
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}