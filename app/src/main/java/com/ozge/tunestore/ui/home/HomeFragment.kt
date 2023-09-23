package com.ozge.tunestore.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozge.tunestore.R
import com.ozge.tunestore.common.gone
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductsAdapter.ProductListener, SaleProductsAdapter.SaleProductListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val productsAdapter by lazy { ProductsAdapter(this) }
    private val productsSaleAdapter by lazy { SaleProductsAdapter(this) }

    private  val viewModel by viewModels<HomeViewModel>()

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.verticalRecyclerView.adapter = productsAdapter
        binding.horizontalRecyclerView.adapter = productsSaleAdapter

        viewModel.getProducts()
        viewModel.getSaleProducts()
        observeData()

        requireActivity().onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    private fun observeData(){
        viewModel.homeState.observe(viewLifecycleOwner){state ->
            when(state){
                HomeViewModel.HomeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is HomeViewModel.HomeState.Data -> {
                    binding.progressBar.gone()
                    productsAdapter.submitList(state.products)
                }
                is HomeViewModel.HomeState.Error ->{
                    binding.progressBar.gone()
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
            }
        }

        viewModel.saleHomeState.observe(viewLifecycleOwner){salestate ->
            when(salestate){
                HomeViewModel.SaleHomeState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is HomeViewModel.SaleHomeState.Data -> {
                    binding.progressBar.gone()
                    productsSaleAdapter.submitList(salestate.products)
                }
                is HomeViewModel.SaleHomeState.Error ->{
                    binding.progressBar.gone()
                    Snackbar.make(requireView(), salestate.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onSaleProductClick(id: Int) {
        val action2 = HomeFragmentDirections.actionHomeFragmentToDetailFragment(id)
        findNavController().navigate(action2)
    }


}