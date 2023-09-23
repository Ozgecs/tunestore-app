package com.ozge.tunestore.data.model.cart

import com.google.gson.annotations.SerializedName

class ClearCartResponse(
    @SerializedName("message")
    val message: String?,
    val clearCartItem: ClearCartItem?,
    @SerializedName("status")
    val status: Int?,
)