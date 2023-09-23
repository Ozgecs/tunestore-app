package com.ozge.tunestore.data.model.cart

import com.google.gson.annotations.SerializedName

class DeleteFromCartResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("cartDeleteItem")
    val cartDeleteItem: CartDeleteItem?,
    @SerializedName("status")
    val status: Int?,
)