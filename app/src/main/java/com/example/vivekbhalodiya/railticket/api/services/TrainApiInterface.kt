package com.example.vivekbhalodiya.railticket.api.services

import com.example.vivekbhalodiya.railticket.api.model.TrainAvailability.TrainAvailResponse
import com.example.vivekbhalodiya.railticket.api.model.TrainBetween.TrainResponse
import com.example.vivekbhalodiya.railticket.api.model.TrainFare.TrainFareResponse
import com.example.vivekbhalodiya.railticket.api.model.TrainLiveStatus.TrainLiveStatusResponse
import com.example.vivekbhalodiya.railticket.api.model.TrainSearchResult.TrainSearchResultResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrainApiInterface {
  @GET("/v2/between/source/{source}/dest/{dest}/date/{date}/apikey/{apikey}")
  fun getTrainBetweenStations(@Path("source") source: String, @Path("dest") dest: String, @Path("date") date: String, @Path(
      "apikey") apikey: String): Observable<Response<TrainResponse>>

  @GET("/v2/fare/train/{train}/source/{source}/dest/{dest}/age/18/pref/{pref}/quota/{quota}/date/{date}/apikey/{apikey}/")
  fun getTrainFareWithAvailability(@Path("train") train: String, @Path("source") source: String, @Path("dest") dest: String
      , @Path("pref") pref: String, @Path("quota") quota: String, @Path("date") date: String
      , @Path("apikey") apikey: String): Observable<Response<TrainFareResponse>>

  @GET("/v2/check-seat/train/{train}/source/{source}/dest/{dest}/date/{date}/pref/{pref}/quota/{quota}/apikey/{apikey}/")
  fun getAvailability(@Path("train") train: String, @Path("source") source: String, @Path("dest") dest: String,
      @Path("date") date: String, @Path("pref") pref: String, @Path("quota") quota: String, @Path("apikey") apikey: String)
      : Observable<Response<TrainAvailResponse>>

  @GET("/v2/suggest-station/name/{name}/apikey/{apikey}/")
  fun getSearchStationRsult(@Path("name") name: String, @Path("apikey") apikey: String): Observable<TrainSearchResultResponse>

  @GET("/v2/live/train/{train}/date/{date}/apikey/{apikey}/")
  fun getTrainLiveStatus(@Path("train") train: String, @Path("date") date: String, @Path("apikey") apikey: String)
      : Observable<TrainLiveStatusResponse>
}