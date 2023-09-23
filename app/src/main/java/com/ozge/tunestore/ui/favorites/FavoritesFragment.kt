package com.ozge.tunestore.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ozge.tunestore.R
import com.ozge.tunestore.common.gone
import com.ozge.tunestore.common.viewBinding
import com.ozge.tunestore.common.visible
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created on 15.09.2023
 * @author Özge Şahin
 */

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoritesAdapter.FavoritesListener  {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val viewModel by viewModels<FavoritesViewModel>()

    private val favoritesAdapter by lazy { FavoritesAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavorites()

        binding.rvFavorites.adapter = favoritesAdapter

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoriteState.Loading -> {
                    progressBar.visible()
                }

                is FavoriteState.Success -> {
                    favoritesAdapter.submitList(state.products)
                    progressBar.gone()
                }

                is FavoriteState.Error -> {
                    ivError.visible()
                    tvError.visible()
                    tvError.text = state.throwable.message.orEmpty()
                    progressBar.gone()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(product: ProductUI) {
        viewModel.deleteProduct(product)
    }
}