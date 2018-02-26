package com.example.vivekbhalodiya.railticket.api.model.TrainAvailability

import com.google.gson.annotations.SerializedName

data class Quota(@SerializedName("code")
                 val code: String = "",
                 @SerializedName("name")
                 val name: String = "")