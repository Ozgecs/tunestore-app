package com.ozge.tunestore.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozge.tunestore.common.Resource
import com.ozge.tunestore.data.model.cart.CartDeleteItem
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productRepository: ProductRepository) :ViewModel() {

    private var _getCartState = MutableLiveData<GetCartState>()
    val getCartState: LiveData<GetCartState>
        get() = _getCartState

    private var _deleteCartState = MutableLiveData<DeleteCartState>()
    val deleteCartState: LiveData<DeleteCartState>
        get() = _deleteCartState

    private var _clearCartState = MutableLiveData<ClearCartState>()
    val clearCartState: LiveData<ClearCartState>
        get() = _clearCartState


    fun getCartProducts(){
        viewModelScope.launch {
            _getCartState.value = GetCartState.Loading

            when(val result = productRepository.getCartProducts()){
                is Resource.Success -> {
                    _getCartState.value = GetCartState.Data(result.data)
                }
                is Resource.Error -> {
                    _getCartState.value = GetCartState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteCartProducts(deleteItem: CartDeleteItem){
        viewModelScope.launch {
            _deleteCartState.value = DeleteCartState.Loading

            when(val result = productRepository.deleteCartProducts(deleteItem)){
                is Resource.Success -> {
                    _deleteCartState.value = DeleteCartState.Data(result.data)
                    getCartProducts()

                }
                is Resource.Error -> {
                    _deleteCartState.value = DeleteCartState.Error(result.throwable)

                }

            }
        }
    }

    fun clearCart(clearCartItem: ClearCartItem){
        viewModelScope.launch{
            _clearCartState.value = ClearCartState.Loading

            when(val result = productRepository.clearCart(clearCartItem)){
                is Resource.Success -> {
                    _clearCartState.value = ClearCartState.Data(result.data)
                    getCartProducts()
                }
                is Resource.Error -> {
                    _clearCartState.value = ClearCartState.Error(result.throwable)

                }
            }
        }
    }


    sealed interface GetCartState{
        object Loading : GetCartState
        data class Data(val products: List<Product?>): GetCartState
        data class Error(val throwable: Throwable): GetCartState
    }
    sealed interface DeleteCartState{
        object Loading : DeleteCartState
        data class Data(val deleteItem: CartDeleteItem): DeleteCartState
        data class Error(val throwable: Throwable): DeleteCartState
    }
    sealed interface ClearCartState{
        object Loading : ClearCartState
        data class Data(val clearCartItem: ClearCartItem): ClearCartState
        data class Error(val throwable: Throwable): ClearCartState
    }


}