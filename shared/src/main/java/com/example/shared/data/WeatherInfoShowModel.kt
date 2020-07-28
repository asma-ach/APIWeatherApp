package com.example.shared.data

import com.example.shared.common.RequestCompleteListener
import com.example.shared.data.model.City
import com.example.shared.data.model.WeatherInfoResponse


interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfoByCityId(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
    fun getWeatherInfoByCityName(city: String, callback: RequestCompleteListener<WeatherInfoResponse>)
}