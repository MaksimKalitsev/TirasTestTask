package ua.zp.tirastesttask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ForecastDayEntity::class, ForecastHourEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
