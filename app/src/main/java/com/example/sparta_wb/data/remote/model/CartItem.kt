package com.example.sparta_wb.data.remote.model

data class CartItem(
    val imageUrl: String,
    val title: String,
    val author: String,
    val price: Double,
    val stock: Int,
    val description: String
)
