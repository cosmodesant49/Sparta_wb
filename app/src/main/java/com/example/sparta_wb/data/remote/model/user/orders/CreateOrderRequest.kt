package com.example.sparta_wb.data.remote.model.user.orders

data class CreateOrderRequest(
    val shippingAddress: ShippingAddress,
    val orderedProducts: List<OrderedProduct>
)

data class ShippingAddress(
    val phone: String,
    val name: String,
    val address: String,
    val city: String,
    val postCode: String,
    val state: String,
    val country: String
)

data class OrderedProduct(
    val id: Long,
    val product_unit_price: Double,
    val product_quantity: Int
)
data class OrderResponse(
    val id: Long,
    val orderAt: String,
    val status: String,
    val shippedAt: String?,
    val deliveredAt: String?,
    val shippingAddress: ShippingAddress,
    val products: List<OrderProduct>
)

data class OrderProduct(
    val id: Long,
    val product_unit_price: String,
    val product_quantity: Int,
    val product: ProductInfo
)

data class ProductInfo(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val stock: Int
)