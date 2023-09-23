package com.ozge.tunestore.data.model.product

data class Product(
    val category: String?,
    val count: Int?,
    val description: String?,
    val id: Int?,
    val imageOne: String?,
    val price: Double?,
    val rate: Double?,
    val salePrice: Double?,
    val saleState: Boolean?,
    val title: String?
)