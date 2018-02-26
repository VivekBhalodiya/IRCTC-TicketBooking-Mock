package com.example.vivekbhalodiya.railticket.api.model.TrainAvailability

import com.google.gson.annotations.SerializedName

data class TrainAvailResponse(@SerializedName("response_code")
                              val responseCode: Int = 0,
                              @SerializedName("quota")
                              val quota: Quota,
                              @SerializedName("from_station")
                              val fromStation: FromStation,
                              @SerializedName("journey_class")
                              val journeyClass: JourneyClass,
                              @SerializedName("availability")
                              val availability: List<AvailabilityItem>?,
                              @SerializedName("debit")
                              val debit: Int = 0,
                              @SerializedName("to_station")
                              val toStation: ToStation,
                              @SerializedName("train")
                              val train: Train)