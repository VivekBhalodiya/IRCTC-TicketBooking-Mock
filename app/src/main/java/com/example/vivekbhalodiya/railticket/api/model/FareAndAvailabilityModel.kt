package com.example.vivekbhalodiya.railticket.api.model

import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.TrainAvailResponse
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.TrainFareResponse
import retrofit2.Response

/**
 * Created by vivekbhalodiya on 2/20/18.
 */
data class FareAndAvailabilityModel(val fareResponse: Response<TrainFareResponse>,val availResponse: Response<TrainAvailResponse>)