package com.example.sparta_wb.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.sparta_wb.data.remote.api.SpartaApiService

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val instance: SpartaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpartaApiService::class.java)
    }
}
