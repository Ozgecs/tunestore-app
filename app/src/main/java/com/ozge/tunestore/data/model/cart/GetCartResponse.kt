package com.ozge.tunestore.data.model.cart

import com.google.gson.annotations.SerializedName
import com.ozge.tunestore.data.model.product.Product

data class GetCartResponse(

    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("status")
    val status: Int?

)
