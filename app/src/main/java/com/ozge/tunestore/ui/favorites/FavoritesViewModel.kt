package com.ozge.tunestore.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozge.tunestore.common.Resource
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private var _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState

    fun getFavorites() {
        viewModelScope.launch {
            _favoriteState.value = FavoriteState.Loading
            when (val result = productRepository.getFavorites()) {
                is Resource.Success -> {
                    _favoriteState.value = FavoriteState.Success(result.data)
                }

                is Resource.Error -> {
                    _favoriteState.value = FavoriteState.Error(result.throwable)
                }
            }
        }
    }

    fun deleteProduct(product: ProductUI) {
        viewModelScope.launch {
            productRepository.deleteFromFavorites(product)
            getFavorites()
        }
    }
}

sealed interface FavoriteState {
    object Loading : FavoriteState
    data class Success(val products: List<ProductUI>) : FavoriteState
    data class Error(val throwable: Throwable) : FavoriteState
}