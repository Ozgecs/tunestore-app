package com.ozge.tunestore.data.model.search

import com.google.gson.annotations.SerializedName
import com.ozge.tunestore.data.model.product.Product

class SearchResponse(

    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("status")
    val status: Int?
)