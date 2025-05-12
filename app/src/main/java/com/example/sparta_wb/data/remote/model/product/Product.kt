package com.example.sparta_wb.data.remote.model.product

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductResponse(
    val totalProducts: Int,
    val limit: Int,
    val products: List<Product>
)

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val stock: Long,
    val images: List<String>,
    val category: Category,
    val review: String,
    val rating: Any?,
    @SerializedName("product_createdAt")
    val productCreatedAt: String,
    @SerializedName("product_updateAt")
    val productUpdateAt: String,
    @SerializedName("product_addedById")
    val productAddedById: Long,
    @SerializedName("product_categoryId")
    val productCategoryId: Long,
    @SerializedName("category_id")
    val categoryId: Long,
    @SerializedName("category_title")
    val categoryTitle: String,
    @SerializedName("category_description")
    val categoryDescription: String,
    @SerializedName("category_createdAt")
    val categoryCreatedAt: String,
    @SerializedName("category_updateAt")
    val categoryUpdateAt: String,
    @SerializedName("category_addedById")
    val categoryAddedById: Long,
    @SerializedName("product_isPopular")
    val productIsPopular: Boolean,
    @SerializedName("product_averageRating")
    val productAverageRating: String,


): Serializable

data class Category(
    val id: Long,
    val title: String,
) : Serializable
