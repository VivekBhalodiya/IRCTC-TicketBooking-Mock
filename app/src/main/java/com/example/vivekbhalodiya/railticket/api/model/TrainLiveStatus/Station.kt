package com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus

import com.google.gson.annotations.SerializedName

data class Station(@SerializedName("code")
                   val code: String = "",
                   @SerializedName("name")
                   val name: String = "")