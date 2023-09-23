package com.ozge.tunestore.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozge.tunestore.common.loadImage
import com.ozge.tunestore.data.model.product.ProductUI
import com.ozge.tunestore.databinding.ItemFavoritesBinding

class FavoritesAdapter(
    private val favoritesListener: FavoritesListener
) : ListAdapter<ProductUI, FavoritesAdapter.FavoritesViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder =
        FavoritesViewHolder(
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            favoritesListener
        )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(getItem(position))

    class FavoritesViewHolder(
        private val binding: ItemFavoritesBinding,
        private val cartProductListener: FavoritesListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvTitle.text = product.title
            tvPrice.text = "${product.price} ₺"

            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                cartProductListener.onProductClick(product.id)
            }

            ivDelete.setOnClickListener {
                cartProductListener.onDeleteClick(product)
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }

    interface FavoritesListener {
        fun onProductClick(id: Int)
        fun onDeleteClick(product: ProductUI)
    }
}