package com.example.vivekbhalodiya.railticket.api.model.TrainBetween

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrainsItem(@SerializedName("number")
                      val number: String = "",
                      @SerializedName("dest_arrival_time")
                      val destArrivalTime: String = "",
                      @SerializedName("classes")
                      val classes: List<ClassesItem>?,
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("days")
                      val days: List<DaysItem>?,
                      @SerializedName("from_station")
                      val fromStation: FromStation,
                      @SerializedName("src_departure_time")
                      val srcDepartureTime: String = "",
                      @SerializedName("travel_time")
                      val travelTime: String = "",
                      @SerializedName("to_station")
                      val toStation: ToStation): Serializable