package ua.zp.tirastesttask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.zp.tirastesttask.data.repository.WeatherRepositoryImpl
import ua.zp.tirastesttask.domain.repository.IWeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): IWeatherRepository
}
