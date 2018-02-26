package com.example.vivekbhalodiya.railticket.api.model.TrainFare

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ClassesItem(@SerializedName("code")
                       val code: String = "",
                       @SerializedName("name")
                       val name: String = "",
                       @SerializedName("available")
                       val available: String = "") : Serializable