package com.skyyeoh.composeweatherforcastapp.di

import android.content.Context
import androidx.room.Room
import com.skyyeoh.composeweatherforcastapp.data.WeatherDao
import com.skyyeoh.composeweatherforcastapp.data.WeatherDatabase
import com.skyyeoh.composeweatherforcastapp.network.WeatherAPI
import com.skyyeoh.composeweatherforcastapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "weather_database"
            ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}