package ua.zp.tirastesttask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.tirastesttask.repository.IWeatherRepository
import ua.zp.tirastesttask.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepository:WeatherRepository
    ): IWeatherRepository
}
