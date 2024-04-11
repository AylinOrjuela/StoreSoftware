package com.example.storesoftware.ui.product.HomeProduct.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storesoftware.databinding.ItemTopProductBinding
import com.example.storesoftware.domain.model.Product

class TopProductsViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val binding = ItemTopProductBinding.bind(view)
    fun render(product: Product){
        binding.apply {
            tvTitle.text = product.name
            Glide.with(tvTitle.context).load(product.imageUrl).into(ivTopProduct)
        }
    }
}