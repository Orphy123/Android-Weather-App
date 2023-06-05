package com.plcoding.weatherapp.domain.repository

import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    // Add the getWeatherDataByCity method to the interface
    suspend fun getWeatherDataByCity(cityName: String): Resource<WeatherInfo>


    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}