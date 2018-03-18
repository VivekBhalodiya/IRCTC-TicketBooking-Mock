package com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus

import com.google.gson.annotations.SerializedName

data class TrainLiveStatusResponse(@SerializedName("response_code")
                                   val responseCode: Int = 0,
                                   @SerializedName("route")
                                   val route: List<RouteItem>?,
                                   @SerializedName("position")
                                   val position: String = "",
                                   @SerializedName("debit")
                                   val debit: Int = 0,
                                   @SerializedName("train")
                                   val train: Train)