package com.example.storesoftware.ui.product.HomeProduct.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storesoftware.databinding.ItemProductBinding
import com.example.storesoftware.domain.model.Product

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProductBinding.bind(view)

    fun render(product: Product) {
        binding.apply {
            Glide.with(binding.tvTitle.context).load(product.imageUrl).into(ivProduct)
            tvTitle.text = product.name
            tvDescription.text = product.description
            tvPrice.text = "${product.price} $"

        }
    }
}