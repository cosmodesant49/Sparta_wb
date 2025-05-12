package com.example.sparta_wb.data.remote.model.user.signIn

data class SigninResponse(
    val accessToken: String,
    val user: User
)
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val roles: List<String>,
    val createdAt: String,
    val updateAt: String
)
