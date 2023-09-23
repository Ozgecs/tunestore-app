package com.ozge.tunestore.data.source.remote

import com.ozge.tunestore.common.Constants.Endpoint.ADD_TO_CART
import com.ozge.tunestore.common.Constants.Endpoint.CLEAR_CART
import com.ozge.tunestore.common.Constants.Endpoint.DELETE_FROM_CART
import com.ozge.tunestore.common.Constants.Endpoint.GET_CART_PRODUCTS
import com.ozge.tunestore.common.Constants.Endpoint.GET_PRODUCTS
import com.ozge.tunestore.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.ozge.tunestore.common.Constants.Endpoint.GET_SALE
import com.ozge.tunestore.common.Constants.Endpoint.SEARCH_PRODUCT
import com.ozge.tunestore.data.model.product.GetProductDetailResponse
import com.ozge.tunestore.data.model.product.GetProductsResponse
import com.ozge.tunestore.data.model.product.GetSaleProductsResponse
import com.ozge.tunestore.data.model.cart.AddToCartResponse
import com.ozge.tunestore.data.model.cart.CartDeleteItem
import com.ozge.tunestore.data.model.cart.CartItem
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.data.model.cart.ClearCartResponse
import com.ozge.tunestore.data.model.cart.DeleteFromCartResponse
import com.ozge.tunestore.data.model.cart.GetCartResponse
import com.ozge.tunestore.data.model.search.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    // Product and detail

    @Headers("store: tunestore")
    @GET(GET_PRODUCTS)
    suspend fun getProducts(@Query("userId") userId: String): GetProductsResponse

    @Headers("store: tunestore")
    @GET(GET_SALE)
    suspend fun getSaleProducts(): GetSaleProductsResponse

    @Headers("store: tunestore")
    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(@Query("id") id: Int): GetProductDetailResponse

    // cart add, get ,delete, clear
    @Headers("store: tunestore")
    @POST(ADD_TO_CART)
    suspend fun addToCart(@Body cartItem: CartItem): AddToCartResponse

    @Headers("store: tunestore")
    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(@Query("userId") userId: String): GetCartResponse

    @Headers("store: tunestore")
    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(@Body cartDeleteItem: CartDeleteItem): DeleteFromCartResponse

    @Headers("store: tunestore")
    @POST(CLEAR_CART)
    suspend fun clearCart(@Body clearCartItem: ClearCartItem): ClearCartResponse

    // search
    @Headers("store: tunestore")
    @GET(SEARCH_PRODUCT)
    fun searchProduct(@Query("query") query: String): Call<SearchResponse>







}