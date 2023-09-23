package com.ozge.tunestore.ui.paymentsuccess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentSuccessViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _psclearCartLiveData = MutableLiveData<ClearCartItem?>()
    val psclearCartLiveData: LiveData<ClearCartItem?>
        get() = _psclearCartLiveData

    private var _errorMessageLiveData = MutableLiveData<String?>()
    val errorMessageLiveData: LiveData<String?>
        get() = _errorMessageLiveData

    init {
        _psclearCartLiveData = productRepository.psclearCartLiveData
        _errorMessageLiveData = productRepository.errorMessageLiveData
    }

}