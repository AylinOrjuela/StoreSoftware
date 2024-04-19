package com.example.storesoftware.ui.sale.addReceiptSale.adapterProductSale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.R
import com.example.storesoftware.domain.model.Product

class ProductSaleAdapter(private var products: List<Product> = emptyList(), val user: String?): RecyclerView.Adapter<ProductSaleViewHolder>(){

    fun updatedList(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product , parent, false)
        return ProductSaleViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductSaleViewHolder, position: Int) {
        val item = products[position]
        val user = user
        if(user != null){
            holder.render(item,user)
        }
    }
}