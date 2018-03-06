package com.example.vivekbhalodiya.railticket.api.model.TrainAvailability

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AvailabilityItem(@SerializedName("date")
                            val date: String = "",
                            @SerializedName("status")
                            val status: String = "") : Serializable