package com.skyyeoh.composeweatherforcastapp.repository

import com.skyyeoh.composeweatherforcastapp.data.WeatherDao
import com.skyyeoh.composeweatherforcastapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun getFavoriteById(city: String): Favorite? = weatherDao.getFavoriteById(city)

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
}