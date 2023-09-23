package com.ozge.tunestore.data.model.product

import com.google.gson.annotations.SerializedName

data class GetProductDetailResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("product")
    val product: Product?,
    @SerializedName("status")
    val status: Int?
)
