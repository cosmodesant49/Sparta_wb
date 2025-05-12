package com.example.sparta_wb.data.remote.model.user.liqid

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LiquidityRequest(
    val averageRating: Double,
    val reviewCount: Int,
    val price: Double
)

data class LiquidityResponse(
    val score: Double,
    val status: String
)

