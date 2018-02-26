package com.example.vivekbhalodiya.railticket.api.model.TrainBetween

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrainResponse(@SerializedName("response_code")
val responseCode: Int = 0,
                         @SerializedName("trains")
                         val trains: ArrayList<TrainsItem>?): Serializable