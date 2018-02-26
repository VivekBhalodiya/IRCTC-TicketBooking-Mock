package com.example.vivekbhalodiya.railticket.api.model.TrainFare

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrainFareResponse(@SerializedName("fare")
                             val fare: Int = 0,
                             @SerializedName("response_code")
                             val responseCode: Int = 0,
                             @SerializedName("quota")
                             val quota: Quota,
                             @SerializedName("from_station")
                             val fromStation: FromStation,
                             @SerializedName("journey_class")
                             val journeyClass: JourneyClass,
                             @SerializedName("debit")
                             val debit: Int = 0,
                             @SerializedName("to_station")
                             val toStation: ToStation,
                             @SerializedName("train")
                             val train: Train) : Serializable