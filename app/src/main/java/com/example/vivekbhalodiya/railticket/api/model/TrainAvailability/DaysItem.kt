package com.example.vivekbhalodiya.railticket.api.model.TrainAvailability

import com.google.gson.annotations.SerializedName

data class DaysItem(@SerializedName("code")
                    val code: String = "",
                    @SerializedName("runs")
                    val runs: String = "")