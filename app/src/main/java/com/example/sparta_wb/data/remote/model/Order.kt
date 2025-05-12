package com.example.sparta_wb.data.remote.model

import java.util.Date

data class OrderResponse(
    val orders: List<Order>
)

data class Order(
    val id: Long,
    val orderAt: Date,
    val status: String,
    val shippedAt: Date?,
    val deliveredAt: Date?,
    val shippingAddress: ShippingAddress,
    val products: List<OrderProduct>
)

data class ShippingAddress(
    val id: Long,
    val phone: String,
    val name: String,
    val address: String,
    val city: String,
    val postCode: String,
    val state: String,
    val country: String
)

data class OrderProduct(
    val id: Long,
    val product_unit_price: String,
    val product_quantity: Int,
    val product: Product
)

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val stock: Int,
    val images: List<String>,
    val createdAt: Date,
    val updateAt: Date
)