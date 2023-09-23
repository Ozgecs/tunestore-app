package com.ozge.tunestore.ui.cart

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
import com.ozge.tunestore.databinding.ItemCartBinding


class CartAdapter(
    private val cartItemListener: CartProductListener
) : ListAdapter<Product, CartAdapter.CartItemViewHolder>(CartItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder =
        CartItemViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            cartItemListener
        )


    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) = holder.bind(getItem(position))

    class CartItemViewHolder(
        private val binding: ItemCartBinding,
        private val cartItemListener: CartProductListener
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {

            with(binding){

                var total = 0.0
                tvTitle.text = product.title
                ivProduct.loadImage(product.imageOne)

                if (product.saleState == true){
                    val price = "${product.price}₺"
                    val spannableString = SpannableString(price)
                    val strikeSpan = StrikethroughSpan()
                    spannableString.setSpan(strikeSpan, 0, price.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvPrice.text = spannableString
                    tvSaleprice.text = "${product.salePrice}₺"

                    total += product.salePrice!!

                }else{
                    tvPrice.text = "${product.price}₺"
                    total += product.price!!
                    tvSaleprice.invisible()
                }


            }

            binding.root.setOnClickListener {
                cartItemListener.onCartProductClick(product.id?: 1)
            }

            binding.ivDelete.setOnClickListener {
                cartItemListener.onDeleteCartItem(product.id?: 1)
            }
        }
    }



    class CartItemCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface CartProductListener {
        fun onCartProductClick(id: Int)
        fun onDeleteCartItem(id: Int)
    }

}

