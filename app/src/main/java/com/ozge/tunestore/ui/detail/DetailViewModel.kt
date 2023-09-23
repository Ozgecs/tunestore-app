package com.ozge.tunestore.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozge.tunestore.common.Resource
import com.ozge.tunestore.data.model.cart.CartItem
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState>
        get() = _detailState

    private var _addCartState = MutableLiveData<AddCartState>()
    val addCartState: LiveData<AddCartState>
        get() = _addCartState


    fun getProductDetail(id: Int){
        viewModelScope.launch {
            _detailState.value = DetailState.Loading

            when(val result = productRepository.getProductDetail(id)){
                is Resource.Success -> {
                    _detailState.value = DetailState.Data(result.data)
                }
                is Resource.Error -> {
                    _detailState.value = DetailState.Error(result.throwable)
                }
            }

        }
    }

    fun addToCart(cartItem: CartItem){
        viewModelScope.launch {
            _addCartState.value = AddCartState.Loading

            when(val result = productRepository.addToCart(cartItem)){
                is Resource.Success -> {
                    _addCartState.value = AddCartState.Data(result.data)
                }
                is Resource.Error -> {
                    _addCartState.value = AddCartState.Error(result.throwable)
                }
            }

        }
    }


    sealed interface DetailState{
        object Loading : DetailState
        data class Data(val product: Product): DetailState
        data class Error(val throwable: Throwable): DetailState
    }
    sealed interface AddCartState{
        object Loading : AddCartState
        data class Data(val cartItem: CartItem): AddCartState
        data class Error(val throwable: Throwable): AddCartState
    }

}