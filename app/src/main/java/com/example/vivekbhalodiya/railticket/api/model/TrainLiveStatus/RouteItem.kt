package com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus

import com.google.gson.annotations.SerializedName

data class RouteItem(@SerializedName("actarr")
                     val actarr: String = "",
                     @SerializedName("no")
                     val no: Int = 0,
                     @SerializedName("distance")
                     val distance: Int = 0,
                     @SerializedName("actarr_date")
                     val actarrDate: String = "",
                     @SerializedName("scharr")
                     val scharr: String = "",
                     @SerializedName("has_arrived")
                     val hasArrived: Boolean = false,
                     @SerializedName("actdep")
                     val actdep: String = "",
                     @SerializedName("has_departed")
                     val hasDeparted: Boolean = false,
                     @SerializedName("latemin")
                     val latemin: Int = 0,
                     @SerializedName("schdep")
                     val schdep: String = "",
                     @SerializedName("station")
                     val station: Station,
                     @SerializedName("day")
                     val day: Int = 0,
                     @SerializedName("scharr_date")
                     val scharrDate: String = "")