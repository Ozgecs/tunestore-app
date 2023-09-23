package com.ozge.tunestore.data.model.product


import com.google.gson.annotations.SerializedName

data class GetProductsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("status")
    val status: Int?
)