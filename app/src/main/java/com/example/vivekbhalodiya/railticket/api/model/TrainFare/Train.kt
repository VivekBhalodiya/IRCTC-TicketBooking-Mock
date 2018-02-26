package com.example.vivekbhalodiya.railticket.api.model.TrainFare

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Train(@SerializedName("number")
                 val number: String = "",
                 @SerializedName("classes")
                 val classes: ArrayList<ClassesItem>?,
                 @SerializedName("name")
                 val name: String = "",
                 @SerializedName("days")
                 val days: List<DaysItem>?): Serializable