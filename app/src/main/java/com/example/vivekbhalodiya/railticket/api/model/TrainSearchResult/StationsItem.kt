package com.example.vivekbhalodiya.railticket.api.model.TrainSearchResult

import com.google.gson.annotations.SerializedName

data class StationsItem(@SerializedName("code")
                        val code: String = "",
                        @SerializedName("name")
                        val name: String = "")