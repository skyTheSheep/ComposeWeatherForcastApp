package com.skyyeoh.composeweatherforcastapp.network

import com.skyyeoh.composeweatherforcastapp.model.Weather
import com.skyyeoh.composeweatherforcastapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY // your api key
    ): Weather
}