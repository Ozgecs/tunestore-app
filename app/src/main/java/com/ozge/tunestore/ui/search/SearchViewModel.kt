package com.ozge.tunestore.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _searchLiveData = MutableLiveData<List<Product?>?>()
    val searchLiveData: LiveData<List<Product?>?>
        get() = _searchLiveData

    private var _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?>
        get() = _errorMessageLiveData


    init {
        _searchLiveData = productRepository.searchLiveData
        _errorMessageLiveData = productRepository.errorMessageLiveData
    }

    fun getSearchProducts(query:String){
        productRepository.getSearchProducts(query)
    }
}