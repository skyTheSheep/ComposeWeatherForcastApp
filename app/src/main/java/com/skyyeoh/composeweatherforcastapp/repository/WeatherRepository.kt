package com.skyyeoh.composeweatherforcastapp.repository

import android.util.Log
import com.bawp.jetweatherforecast.model.Weather
import com.skyyeoh.composeweatherforcastapp.data.DataOrException
import com.skyyeoh.composeweatherforcastapp.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI) {

    suspend fun getWeather(cityQuery: String, units: String)
            :DataOrException<Weather, Boolean, Exception>  {
        val response = try {
            api.getWeather(query = cityQuery, units = units)

        }catch (e: Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return  DataOrException(data = response)

    }
}