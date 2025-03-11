package com.example.sparta_wb.data.remote.api

import retrofit2.Call
import com.example.sparta_wb.data.remote.model.user.signUp.SignupRequest
import com.example.sparta_wb.data.remote.model.user.signUp.SignupResponse
import com.example.sparta_wb.utils.costants.Constants.SIGNUP
import retrofit2.http.Body
import retrofit2.http.POST

interface SpartaApiService {
    @POST(SIGNUP)
    fun signup(@Body request: SignupRequest): Call<SignupResponse>
}