package com.example.shared.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
        @SerializedName("city")
        val name: String = "",
        @SerializedName("country")
        val country: String = "",
        @SerializedName("iso2")
        val iso2: String = ""

): Serializable