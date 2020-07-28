package com.example.openweathermapapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shared.common.RequestCompleteListener
import com.example.shared.data.model.City
import com.example.shared.data.model.WeatherData
import com.example.shared.data.model.WeatherInfoResponse
import com.example.shared.data.WeatherInfoShowModel
import com.example.shared.utils.kelvinToCelsius
import com.example.shared.utils.unixTimestampToDateTimeString
import com.example.shared.utils.unixTimestampToTimeString

/**
 * ViewModel to manage weather data
 */
class WeatherInfoViewModel : ViewModel()  {

    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()


    fun getCityList(model: WeatherInfoShowModel) {

        model.getCityList(object :
            RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data)
            }

            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage)
            }
        })
    }

    fun getWeatherInfo(city: String, model: WeatherInfoShowModel) {

        progressBarLiveData.postValue(true)

        model.getWeatherInfoByCityName(city, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false)

                weatherInfoLiveData.postValue(weatherData)
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false)
                weatherInfoFailureLiveData.postValue(errorMessage)
            }
        })
    }
}