package com.ozge.tunestore.ui.home

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozge.tunestore.common.loadImage
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.databinding.ItemProductBinding

class SaleProductsAdapter(
    private val saleProductListener: SaleProductsAdapter.SaleProductListener
) :ListAdapter<Product, SaleProductsAdapter.ProductSaleViewHolder>(ProductSaleCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaleViewHolder =
        ProductSaleViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            saleProductListener
        )

    override fun onBindViewHolder(holder: ProductSaleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductSaleViewHolder(
        private val binding: ItemProductBinding,
        private val saleProductListener: SaleProductListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvTitle.text = product.title
                val price = "${product.price}₺"
                val spannableString = SpannableString(price)
                val strikeSpan = StrikethroughSpan()
                spannableString.setSpan(strikeSpan, 0, price.length, Spanned.SPAN_COMPOSING)
                tvPrice.text = spannableString
                tvPrice3.text = "${product.salePrice}₺"
                ivProduct.loadImage(product.imageOne)

                root.setOnClickListener {
                    saleProductListener.onSaleProductClick(product.id ?: 1)
                }
            }
        }
    }

    class ProductSaleCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface SaleProductListener {
        fun onSaleProductClick(id: Int)
    }

}