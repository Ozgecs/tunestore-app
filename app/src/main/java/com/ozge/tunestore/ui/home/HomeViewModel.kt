package com.ozge.tunestore.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozge.tunestore.common.Resource
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState

    private var _saleHomeState = MutableLiveData<SaleHomeState>()
    val saleHomeState: LiveData<SaleHomeState>
        get() = _saleHomeState

    fun getProducts(){
        viewModelScope.launch {
            _homeState.value = HomeState.Loading

            when(val result = productRepository.getProducts()){
                is Resource.Success -> {
                    _homeState.value = HomeState.Data(result.data)
                }
                is Resource.Error -> {
                    _homeState.value = HomeState.Error(result.throwable)
                }
            }
        }

    }
    fun getSaleProducts(){
        viewModelScope.launch {
            _saleHomeState.value = SaleHomeState.Loading

            when(val result2 = productRepository.getSaleProducts()){
                is Resource.Success ->{
                    _saleHomeState.value = SaleHomeState.Data(result2.data)
                }
                is Resource.Error ->{
                    _saleHomeState.value = SaleHomeState.Error(result2.throwable)
                }
            }
        }

    }

    sealed interface HomeState{
        object Loading : HomeState
        data class Data(val products: List<Product?>): HomeState
        data class Error(val throwable: Throwable): HomeState
    }
    sealed interface SaleHomeState{
        object Loading : SaleHomeState
        data class Data(val products: List<Product?>): SaleHomeState
        data class Error(val throwable: Throwable): SaleHomeState
    }
}

