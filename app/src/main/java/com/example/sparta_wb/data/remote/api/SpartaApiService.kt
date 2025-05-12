package com.example.sparta_wb.data.remote.api

import com.example.sparta_wb.data.remote.model.product.Product
import com.example.sparta_wb.data.remote.model.product.ProductResponse
import com.example.sparta_wb.data.remote.model.user.signIn.SigninRequest
import com.example.sparta_wb.data.remote.model.user.signIn.SigninResponse
import retrofit2.Call
import com.example.sparta_wb.data.remote.model.user.signUp.SignupRequest
import com.example.sparta_wb.data.remote.model.user.signUp.SignupResponse
import com.example.sparta_wb.utils.costants.Constants.GETPRODUCT
import com.example.sparta_wb.utils.costants.Constants.SIGNIN
import com.example.sparta_wb.utils.costants.Constants.SIGNUP
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpartaApiService {
    @POST(SIGNUP)
    fun signup(@Body request: SignupRequest): Call<SignupResponse>

    @POST(SIGNIN)
    fun signin(@Body request: SigninRequest): Call<SigninResponse>

    @GET(GETPRODUCT)
    fun getProducts(): Call<ProductResponse>
}