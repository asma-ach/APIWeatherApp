package com.example.shared.network

import com.example.shared.common.Constant
import com.example.shared.data.model.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    //https://api.openweathermap.org/data/2.5/weather?id=5056033&appid=8118ed6ee68db2debfaaa5a44c832918
    @GET("weather")
    fun callWeatherInfoByCityId(@Query("id") cityId: Int): Call<WeatherInfoResponse>

    //https://api.openweathermap.org/data/2.5/weather?q=Rome,it&units=metric&appid=8118ed6ee68db2debfaaa5a44c832918
    @GET(Constant.URL_DATA_WEATHER)
    fun getWeatherInfoByCityCountry(@Query("q") q: String?, @Query("units") units: String?):  Call<WeatherInfoResponse>
}