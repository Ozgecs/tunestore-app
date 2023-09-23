package com.ozge.tunestore.ui.search

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
import com.ozge.tunestore.databinding.ItemSearchProductBinding

class SearchAdapter(
    private val searchListener: SearchListener
) : ListAdapter<Product, SearchAdapter.SearchViewHolder>(SearchDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            searchListener
        )


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding: ItemSearchProductBinding,
        private val searchListener: SearchListener
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product) = with(binding){

            ivTitle.text = product.title
            ratingBar3.rating = product.rate!!.toFloat()
            val price = "${product.price}₺"
            if (product.saleState == true){
                val spannableString = SpannableString(price)
                val strikeSpan = StrikethroughSpan()
                spannableString.setSpan(strikeSpan, 0, price.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                tvPrice.text = spannableString
                tvSaleprice.text = "${product.salePrice}₺"

            }else{
                tvPrice.text = price
                tvSaleprice.invisible()
            }
            ivProduct.loadImage(product.imageOne)

            root.setOnClickListener {
                searchListener.onSearchItemClick(product.id?: 1)
            }

        }

    }

    class SearchDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface SearchListener {
        fun onSearchItemClick(id: Int)
    }
}