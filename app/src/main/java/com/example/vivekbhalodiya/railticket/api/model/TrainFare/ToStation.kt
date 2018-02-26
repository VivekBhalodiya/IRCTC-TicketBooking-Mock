package com.example.vivekbhalodiya.railticket.api.model.TrainFare

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ToStation(@SerializedName("code")
                     val code: String = "",
                     @SerializedName("name")
                     val name: String = ""): Serializable