package com.ozge.tunestore.ui.home

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozge.tunestore.common.invisible
import com.ozge.tunestore.common.loadImage
import com.ozge.tunestore.data.model.product.Product
import com.ozge.tunestore.databinding.ItemProductBinding

class ProductsAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, ProductsAdapter.ProductViewHolder>(ProductDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val productListener: ProductListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product) = with(binding){
            tvTitle.text = product.title
            if (product.saleState == true){
                val price = "${product.price}₺"
                val spannableString = SpannableString(price)
                val strikeSpan = StrikethroughSpan()
                spannableString.setSpan(strikeSpan, 0, price.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                tvPrice.text = spannableString
                tvPrice3.text = "${product.salePrice}₺"

            }else{
                tvPrice.text = "${product.price}₺"
                tvPrice3.invisible()
            }
            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                productListener.onProductClick(product.id?: 1)
            }

        }

    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductListener {
        fun onProductClick(id: Int)
    }
}