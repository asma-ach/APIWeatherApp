package com.example.openweathermapapp.domain.local

import androidx.room.TypeConverter
import com.example.openweathermapapp.domain.datamodel.CountryModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class RoomDataConverter : Serializable {

    @TypeConverter
    fun stringFromObject(list: CountryModel?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getObjectFromString(jsonString: String?): CountryModel? {
        val listType: Type = object : TypeToken<CountryModel?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }


    @TypeConverter
    fun stringFromListObject(list: List<CountryModel?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun getListObjectFromString(jsonString: String?): List<CountryModel?>? {
        val listType: Type = object : TypeToken<ArrayList<CountryModel?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }
}