package com.example.openweathermapapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shared.common.RequestCompleteListener
import com.example.shared.data.WeatherInfoShowModel
import com.example.shared.data.model.City

class TownListViewModel : ViewModel()  {

    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()


    fun test(){}

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
}