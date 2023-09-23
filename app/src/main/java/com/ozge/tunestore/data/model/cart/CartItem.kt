package com.ozge.tunestore.data.model.cart

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("productId")
    val productId: Int?,
    @SerializedName("userId")
    val userId: String?
)