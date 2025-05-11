package com.example.sparta_wb.data.remote.model.product

import java.io.Serializable

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val stock: Int,
    val images: List<String>,
    val createdAt: String,
    val updateAt: String
) : Serializable
