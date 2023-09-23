package com.ozge.tunestore.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.data.model.cart.CartDeleteItem
import com.ozge.tunestore.data.model.cart.ClearCartItem
import com.ozge.tunestore.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */
@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.CartProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val cartAdapter by lazy { CartAdapter(this) }

    private lateinit var auth: FirebaseAuth

    private  val viewModel by viewModels<CartViewModel>()

    private var totalPrice = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        observeData()
        binding.rvCart.adapter = cartAdapter
        binding.btnClearBag.setOnClickListener {
            val clearCartItem = ClearCartItem(auth.currentUser?.uid)
            viewModel.clearCart(clearCartItem)
        }
        binding.btnBuy.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToPaymentFragment()
            findNavController().navigate(action)
        }
        updateTotalPriceTextView()
        viewModel.getCartProducts()

    }

    private fun updateTotalPriceTextView() {
        val totalPriceString = getString(R.string.total_price_format,String.format("%.3f", totalPrice))
        binding.tvTotal.text = totalPriceString

    }

    private fun observeData() = with(binding){
        viewModel.getCartState.observe(viewLifecycleOwner) { list ->
            when(list){
                CartViewModel.GetCartState.Loading ->{
                    //
                }
                is CartViewModel.GetCartState.Data ->{
                    if (list.products.isEmpty().not()){
                        cartAdapter.submitList(list.products)

                        totalPrice = list.products.mapNotNull { product ->
                            if (product?.saleState == true) {
                                product.salePrice ?: 0.0
                            } else {
                                product?.price ?: 0.0
                            }
                        }.sum()
                        updateTotalPriceTextView()

                    }else{
                        btnBuy.isEnabled = false
                        cartAdapter.submitList(list.products)
                        totalPrice = 0.0
                        updateTotalPriceTextView()
                    }
                }
                is CartViewModel.GetCartState.Error ->{
                    Snackbar.make(requireView(), list.throwable.message.orEmpty(), 1000).show()
                }
            }
        }

        viewModel.deleteCartState.observe(viewLifecycleOwner){delete->
            when(delete){
                CartViewModel.DeleteCartState.Loading ->{

                }
                is CartViewModel.DeleteCartState.Data -> {

                }
                is CartViewModel.DeleteCartState.Error -> {
                    Snackbar.make(requireView(), delete.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
        viewModel.clearCartState.observe(viewLifecycleOwner){clear ->
            when(clear){
                CartViewModel.ClearCartState.Loading ->{

                }
                is CartViewModel.ClearCartState.Data -> {

                }
                is CartViewModel.ClearCartState.Error -> {
                    Snackbar.make(requireView(), clear.throwable.message.orEmpty(), 1000).show()
                }
            }
        }


    }

    override fun onCartProductClick(id: Int) {
        val action = CartFragmentDirections.actionCartFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteCartItem(id: Int) {
        val cartDeleteItem = CartDeleteItem(id)
        viewModel.deleteCartProducts(cartDeleteItem)
    }

}