package com.example.openweathermapapp.domain.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.openweathermapapp.domain.datamodel.CountryModel


@Dao
interface AppRoomDao {

    @Query("SELECT * FROM Country")
    fun getAllCountries(): LiveData<List<CountryModel?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: CountryModel?)

}