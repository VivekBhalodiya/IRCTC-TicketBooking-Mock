package com.example.vivekbhalodiya.railticket.api.model.TrainAvailability

import com.google.gson.annotations.SerializedName

data class JourneyClass(@SerializedName("code")
                        val code: String = "",
                        @SerializedName("name")
                        val name: String = "")