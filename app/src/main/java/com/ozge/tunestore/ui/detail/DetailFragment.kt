package com.ozge.tunestore.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.invisible
import com.ozge.tunestore.common.loadImage
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.data.model.cart.CartItem
import com.ozge.tunestore.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        viewModel.getProductDetail(args.id)

        observeData()

        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeData() = with(binding){
        viewModel.detailState.observe(viewLifecycleOwner){state ->
            when(state){
                DetailViewModel.DetailState.Loading ->{
                    //
                }
                is DetailViewModel.DetailState.Data -> {
                    tvTitle2.text = state.product.title
                    tvCategory2.text = state.product.category
                    if (state.product.saleState == true) {
                        val price = "${state.product.price}₺"
                        val spannableString = SpannableString(price)
                        val strikeSpan = StrikethroughSpan()
                        spannableString.setSpan(
                            strikeSpan,
                            0,
                            price.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        tvPrice6.text = spannableString
                        tvPrice5.text = "${state.product.salePrice}₺"

                    } else {
                        tvPrice6.text = "${state.product.price}₺"
                        tvPrice5.invisible()
                    }
                    tvDescription2.text = state.product.description
                    ratingBar2.rating = state.product.rate!!.toFloat()
                    imageView4.loadImage(state.product.imageOne)

                    btnAddToCart.setOnClickListener {
                        val cartItem = CartItem(state.product.id, auth.currentUser?.uid)
                        viewModel.addToCart(cartItem)
                    }
                }
                is DetailViewModel.DetailState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
        viewModel.addCartState.observe(viewLifecycleOwner){cart->
            when(cart){
                DetailViewModel.AddCartState.Loading -> {
                    //
                }

                is DetailViewModel.AddCartState.Data -> {
                    Toast.makeText(requireContext(),"Ürün Başarıyla Eklendi",Toast.LENGTH_SHORT)
                }

                is DetailViewModel.AddCartState.Error -> {
                    Snackbar.make(requireView(), cart.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
    }

}