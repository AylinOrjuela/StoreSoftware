package com.example.storesoftware.ui.product.editDeleteProduct.adapterSelectProduct

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storesoftware.databinding.ItemProductBinding
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.ui.product.editDeleteProduct.DeleteEditProductActivity

class SelectProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemProductBinding.bind(view)
    fun render(product: Product, user: String) {
        val context = binding.cvProduct.context

        binding.apply {
            tvTitle.text = product.name
            Glide.with(tvTitle.context).load(product.imageUrl).into(ivProduct)
            tvDescription.text = product.description
            tvPrice.text = product.stock.toString()
        }

        binding.cvProduct.setOnClickListener {
            val intent = Intent(context, DeleteEditProductActivity::class.java)
            intent.putExtra("userId", user)
            intent.putExtra("productKey", product.id)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}