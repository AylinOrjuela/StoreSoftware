package com.example.storesoftware.ui.product.HomeProduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.R
import com.example.storesoftware.domain.model.Product

class TopProductsAdater(private var products: List<Product> = emptyList()) :
    RecyclerView.Adapter<TopProductsViewHolder>() {

    fun updatedList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_product, parent, false)
        return TopProductsViewHolder(view)
    }

    override fun getItemCount(): Int = products.size


    override fun onBindViewHolder(holder: TopProductsViewHolder, position: Int) {
        holder.render(products[position])
    }

}