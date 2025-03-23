package com.example.sparta_wb.data.remote.model.user.signIn

data class SigninResponse(
    val success: Boolean,
    val message: String,
    val userId: String?
)