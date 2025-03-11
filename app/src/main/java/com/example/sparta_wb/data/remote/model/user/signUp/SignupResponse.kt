package com.example.sparta_wb.data.remote.model.user.signUp

data class SignupResponse(
    val success: Boolean,
    val message: String,
    val userId: String?
)