package com.example.storesoftware.ui.sale.addReceiptSale.adapterProductSale

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storesoftware.databinding.ItemProductBinding
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.ui.sale.addReceiptSale.CreateSaleActivity

class ProductSaleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
            val intent = Intent(context, CreateSaleActivity::class.java)
            intent.putExtra("userId", user)
            intent.putExtra("productKey", product.id)
            context.startActivity(intent)
            (context as Activity).finish()
        }

    }
}