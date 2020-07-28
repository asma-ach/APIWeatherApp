package com.example.openweathermapapp.domain.datamodel

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Country")
data class CountryModel (@field:ColumnInfo(name = "name") @field:NonNull @field:PrimaryKey @param:NonNull var name: String? = null,
                         var id: String? = null)

