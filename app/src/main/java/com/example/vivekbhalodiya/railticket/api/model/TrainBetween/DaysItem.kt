package com.example.vivekbhalodiya.railticket.api.model.TrainBetween

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DaysItem(@SerializedName("code")
                    val code: String? = "",
                    @SerializedName("runs")
                    val runs: String? = ""): Serializable