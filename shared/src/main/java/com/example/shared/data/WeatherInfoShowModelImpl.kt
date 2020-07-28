package com.example.shared.data

import android.content.Context
import com.example.shared.common.RequestCompleteListener
import com.example.shared.data.model.City
import com.example.shared.data.model.WeatherInfoResponse
import com.example.shared.network.ApiInterface
import com.example.shared.network.RetrofitClient
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context):
    WeatherInfoShowModel {

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list_fr.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents  = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }

    override fun getWeatherInfoByCityId(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {
        TODO("Not yet implemented")
    }

    override fun getWeatherInfoByCityName(cityCountry: String, callback: RequestCompleteListener<WeatherInfoResponse>) {

        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.getWeatherInfoByCityCountry(cityCountry,"metric")

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(call: Call<WeatherInfoResponse>, response: Response<WeatherInfoResponse>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!)
                else
                    callback.onRequestFailed(response.message())
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}