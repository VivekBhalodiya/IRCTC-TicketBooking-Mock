package com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus

import com.google.gson.annotations.SerializedName

data class Train(@SerializedName("number")
                 val number: String = "",
                 @SerializedName("name")
                 val name: String = "")