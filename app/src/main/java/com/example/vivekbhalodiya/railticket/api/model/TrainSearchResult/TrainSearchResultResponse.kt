package com.example.vivekbhalodiya.railticket.api.model.TrainSearchResult

import com.google.gson.annotations.SerializedName

data class TrainSearchResultResponse(@SerializedName("response_code")
                                     val responseCode: Int = 0,
                                     @SerializedName("total")
                                     val total: Int = 0,
                                     @SerializedName("stations")
                                     val stations: List<StationsItem>?,
                                     @SerializedName("debit")
                                     val debit: Int = 0)