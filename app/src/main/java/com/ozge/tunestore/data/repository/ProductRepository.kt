package com.ozge.tunestore.data.repository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.common.Resource
import com.ozge.tunestore.data.mapper.mapToProductEntity
import com.ozge.tunestore.data.mapper.mapToProductUI
import com.ozge.tunestore.data.model.cart.CartDeleteItem
import com.ozge.tunestore.data.model.cart.CartItem
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.data.model.search.SearchResponse
import com.ozge.tunestore.data.source.local.ProductDao
import com.ozge.tunestore.data.source.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository (
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    private lateinit var auth: FirebaseAuth

    // Cart
    val errorMessageLiveData = MutableLiveData<String?>()

    // Payment Success
    val psclearCartLiveData = MutableLiveData<ClearCartItem?>()

    // Search
    val searchLiveData = MutableLiveData<List<Product?>?>()


    // BUNU TAMAMLADIM -> HOMEVİEW
    suspend fun getProducts(): Resource<List<Product?>> {
        auth = Firebase.auth
        return try {
            val result = productService.getProducts(auth.currentUser!!.uid).products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found!"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    // TAMAMLADIM -> HOMEVİEW
    suspend fun getSaleProducts(): Resource<List<Product?>> {
        return try {
            val result = productService.getSaleProducts().products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found!"))
            } else {
                Resource.Success(result.filter { it?.saleState == true })
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    // BUNU TAMAMLADIM -> DETAILVIEW
    suspend fun getProductDetail(id: Int): Resource<Product> {
        return try {
            productService.getProductDetail(id).product?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Product not found!"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    // TAMAMLANDI -> DETAİLVİEW
    suspend fun addToCart(cartItem: CartItem): Resource<CartItem> {
        return try {
            val response = productService.addToCart(cartItem)
            val cartItemResponse = response.cartItem
            if (cartItemResponse != null) {
                Resource.Error(Exception("Ürün Eklemesi Başarısız"))
            } else {
                Resource.Error(Exception("Ürün Eklendi"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCartProducts(): Resource<List<Product?>> {
        auth = Firebase.auth
        return try {
            val result = productService.getCartProducts(auth.currentUser!!.uid).products
            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Ürün Bulunamadı"))
            } else {
                Resource.Success(result)

            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteCartProducts(deleteItem: CartDeleteItem): Resource<CartDeleteItem> {
        return try {
            val response = productService.deleteFromCart(deleteItem)
            val deleteCartItemResponse = response.cartDeleteItem
            if (deleteCartItemResponse != null) {
                Resource.Error(Exception("Ürün Silme Başarısız"))
            } else {
                Resource.Error(Exception("Ürün Silindi"))

            }
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    suspend fun clearCart(clearCartItem: ClearCartItem): Resource<ClearCartItem> {
        return try {
            val response = productService.clearCart(clearCartItem)
            val clearCartItemResponse = response.clearCartItem
            if (clearCartItemResponse != null) {
                Resource.Error(Exception("Sepet Temizlenemedi"))
            } else {
                Resource.Error(Exception("Sepet Temizlendi"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getSearchProducts(query: String) {
        productService.searchProduct(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                searchLiveData.value = response.body()?.products
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }

        })
    }

    fun addToFavorites(product: ProductUI) {
        productDao.addToFavorites(product.mapToProductEntity())
    }

    fun getFavorites(): Resource<List<ProductUI>> =
        try {
            val products = productDao.getProducts().map {
                it.mapToProductUI()
            }
            Resource.Success(products)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    fun deleteFromFavorites(product: ProductUI) {
        productDao.deleteFromFavorites(product.mapToProductEntity())

    }
}