package com.example.vivekbhalodiya.railticket.api.services

import com.example.vivekbhalodiya.railticket.constants.AppConstant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by vivekbhalodiya on 2/15/18.
 */
class TrainApi{
  private lateinit var retrofit: Retrofit

  fun getClient(): Retrofit {
      retrofit = Retrofit.Builder()
          .baseUrl(AppConstant.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
    return retrofit
  }
}