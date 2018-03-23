package com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Station(@SerializedName("code")
                   val code: String = "",
                   @SerializedName("name")
                   val name: String = "") : Serializable