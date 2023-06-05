package com.plcoding.weatherapp.data.repository


import com.plcoding.weatherapp.data.remote.CityCoordinatesApi
import com.plcoding.weatherapp.data.mappers.toWeatherInfo
import com.plcoding.weatherapp.data.remote.NominatimLocationDto
import com.plcoding.weatherapp.data.remote.WeatherApi
import com.plcoding.weatherapp.domain.repository.WeatherRepository
import com.plcoding.weatherapp.domain.util.Resource
import com.plcoding.weatherapp.domain.weather.WeatherInfo
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject




class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val cityCoordinatesApi: CityCoordinatesApi
): WeatherRepository {

    private suspend fun getCoordinates(cityName: String): Pair<Double, Double>? = suspendCoroutine { continuation ->
        try {
            val call = cityCoordinatesApi.getCityCoordinates(cityName, "json", 1, "city")
            call.enqueue(object : Callback<List<NominatimLocationDto>> {
                override fun onResponse(call: Call<List<NominatimLocationDto>>, response: Response<List<NominatimLocationDto>>) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                        val location = response.body()!![0]
                        val lat = location.lat.toDoubleOrNull() ?: 0.0
                        val lon = location.lon.toDoubleOrNull() ?: 0.0
                        continuation.resume(Pair(lat, lon))
                    } else {
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<List<NominatimLocationDto>>, t: Throwable) {
                    t.printStackTrace()
                    continuation.resume(null)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            continuation.resume(null)
        }
    }




    override suspend fun getWeatherDataByCity(cityName: String): Resource<WeatherInfo> {
        val coordinates = getCoordinates(cityName)
        return if (coordinates != null) {
            getWeatherData(coordinates.first, coordinates.second)
        } else {
            Resource.Error("Couldn't fetch coordinates for the given city name.")
        }
    }

    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}