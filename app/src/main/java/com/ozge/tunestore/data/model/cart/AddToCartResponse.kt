package com.ozge.tunestore.data.model.cart

import com.google.gson.annotations.SerializedName

data class AddToCartResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("cartItem")
    val cartItem: CartItem, // İsteğin gövdesi olarak eklenen veri
    @SerializedName("status")
    val status: Int?,
)