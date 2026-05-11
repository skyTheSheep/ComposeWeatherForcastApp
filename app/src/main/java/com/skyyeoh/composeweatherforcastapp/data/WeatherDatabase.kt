package com.skyyeoh.composeweatherforcastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skyyeoh.composeweatherforcastapp.model.Favorite
import com.skyyeoh.composeweatherforcastapp.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun  weatherDao(): WeatherDao
}