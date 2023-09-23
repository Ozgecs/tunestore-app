package com.ozge.tunestore.ui.paymentsuccess

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.databinding.FragmentPaymentSuccessBinding
import com.ozge.tunestore.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */

@AndroidEntryPoint
class PaymentSuccessFragment : Fragment(R.layout.fragment_payment_success) {

    private val binding by viewBinding(FragmentPaymentSuccessBinding::bind)

    private  val viewModel by viewModels<CartViewModel>()

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val clearCartItem = ClearCartItem(auth.currentUser?.uid)

        with(binding) {

            btnContinue.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.clearCart(clearCartItem)
                }
                val action = PaymentSuccessFragmentDirections.actionPaymentSuccessFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action = PaymentSuccessFragmentDirections.actionPaymentSuccessFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }
        )
    }

}